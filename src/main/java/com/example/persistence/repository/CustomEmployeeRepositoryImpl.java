package com.example.persistence.repository;

import com.example.controller.contract.EmployeeDetailsResponseContract;
import com.example.exception.EmployeeNotPresentException;
import com.example.persistence.entity.Employee;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

  private static final String QUERY_TO_FETCH_ALL_REPORTEES =
      "with recursive employeepaths as (\n" +
          "   select id,`name`,age, reports_to, grade from employee where id= :managerId\n" +
          "   union all\n" +
          "   select e.id,e.`name`,e.age, e.reports_to, e.grade from employee e\n" +
          "   inner join employeepaths ep on ep.id = e.reports_to\n" +
          ")\n" +
          "select id,`name`,age, reports_to, grade from employeepaths where id <> :managerId;";

  private static final String QUERY_TO_FETCH_EMPLOYEE_DETAILS_WITH_GRADE =
      "SELECT e.id,e.`name`,e.age,e.reports_to,e.grade,g.salary,g.`role` " +
          "FROM  employee e " +
          "INNER JOIN " +
          "grade g " +
          "ON e.grade = g.id AND e.id= :employeeId;";

  private final DatabaseClient client;

  public CustomEmployeeRepositoryImpl(DatabaseClient client) {
    this.client = client;
  }

  @Override
  public Flux<Employee> findAllIndirectAndDirectReportees(Long managerId) {
    return client.execute(QUERY_TO_FETCH_ALL_REPORTEES)
        .bind("managerId", managerId)
        .map(row -> {
          Long id = row.get("id", Long.class);
          String name = row.get("name", String.class);
          Integer age = Integer.parseInt(row.get("age", String.class));
          Long reportsTo = row.get("reports_to", Long.class);
          String grade = row.get("grade", String.class);
          Employee employee = new Employee();
          employee.setId(id);
          employee.setName(name);
          employee.setAge(age);
          employee.setReportsTo(reportsTo);
          employee.setGrade(grade);
          return employee;
        }).all();

  }

  @Override
  public Mono<EmployeeDetailsResponseContract> getDetails(Long employeeId) {
    return client.execute(QUERY_TO_FETCH_EMPLOYEE_DETAILS_WITH_GRADE)
        .bind("employeeId", employeeId)
        .map(row -> {
          EmployeeDetailsResponseContract responseContract = new EmployeeDetailsResponseContract();
          responseContract.setId(employeeId);
          responseContract.setAge(Integer.parseInt(row.get("age", String.class)));
          responseContract.setName(row.get("name", String.class));
          responseContract.setGrade(row.get("grade", String.class));
          responseContract.setReportsTo(row.get("reports_to", Long.class));
          responseContract.setRole(row.get("role", String.class));
          responseContract.setSalary(row.get("salary", Integer.class));
          return responseContract;
        })
        .one()
        .switchIfEmpty(Mono.error(new EmployeeNotPresentException()));
  }
}

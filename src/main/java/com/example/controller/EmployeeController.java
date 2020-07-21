package com.example.controller;

import com.example.controller.contract.EmployeeRequestContract;
import com.example.exception.EmployeeNotPresentException;
import com.example.exception.GradeNotPresentException;
import com.example.exception.ManagerNotPresentException;
import com.example.exception.MultipleRootEmployeeException;
import com.example.exception.SelfReportingException;
import com.example.persistence.entity.Employee;
import com.example.persistence.repository.EmployeeRepository;
import com.example.persistence.repository.GradeRepository;
import com.example.persistence.translator.EmployeeTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class EmployeeController {

  private final EmployeeRepository employeeRepository;
  private final GradeRepository gradeRepository;
  private final EmployeeTranslator employeeTranslator = new EmployeeTranslator();

  @Autowired
  public EmployeeController(EmployeeRepository employeeRepository, GradeRepository gradeRepository) {
    this.employeeRepository = employeeRepository;
    this.gradeRepository = gradeRepository;
  }

  @PostMapping("/{tenant}/api/employee")
  public Mono<ResponseEntity> create(@Valid @RequestBody Mono<EmployeeRequestContract> employeeMono) {
    return employeeMono.map(employeeTranslator::translateTo)
        .flatMap(employee -> Mono.zip(validateReportsTo(employee),
            gradeRepository.checkIfGradeExistsForGivenId(employee.getGrade())))
        .map(tuple -> {
          Boolean doesGradeExistInDb = tuple.getT2();
          if (doesGradeExistInDb) {
            return tuple.getT1();
          } else {
            throw new GradeNotPresentException();
          }
        })
        .flatMap(employeeRepository::save)
        .flatMap(savedEmployee -> Mono.just(ResponseEntity.created(URI.create("/api/employee/" + savedEmployee.getId())).build())); //Returns the resource URI as response header
  }

  @PutMapping("/{tenant}/api/employee/{id}")
  public Mono<ResponseEntity> update(
      @PathVariable("id") Long employeeId,
      @Valid @RequestBody Mono<EmployeeRequestContract> employeeMono) {

    return employeeRepository.findById(employeeId)
        .switchIfEmpty(Mono.error(new EmployeeNotPresentException()))
        .then(employeeMono)
        .map(employeeRequestContract -> {
          Employee employee = employeeTranslator.translateTo(employeeRequestContract);
          employee.setId(employeeId);
          return employee;
        }).flatMap(employee -> Mono.zip(validateReportsTo(employee, employeeId),
            gradeRepository.checkIfGradeExistsForGivenId(employee.getGrade())))
        .map(tuple -> {
          Boolean doesGradeExistInDb = tuple.getT2();
          if (doesGradeExistInDb) {
            return tuple.getT1();
          } else {
            throw new GradeNotPresentException();
          }
        })
        .flatMap(employeeRepository::save)
        .flatMap(savedEmployee -> Mono.just(ResponseEntity.ok("Employee " + employeeId + " updated successfully.")));

  }

  @GetMapping("/{tenant}/api/employee/{id}/reportees/direct")
  public Mono<ResponseEntity> getAllDirectReportees(@PathVariable("id") Long employeeId) {
    return employeeRepository.findById(employeeId)
        .switchIfEmpty(Mono.error(new EmployeeNotPresentException()))
        .thenMany(employeeRepository.findByReportsTo(employeeId))
        .collectList()
        .map(ResponseEntity::ok);
  }

  @GetMapping("/{tenant}/api/employee/{id}/reportees/all")
  public Mono<ResponseEntity> getAllReportees(@PathVariable("id") Long employeeId) {
    return employeeRepository.findById(employeeId)
        .switchIfEmpty(Mono.error(new EmployeeNotPresentException()))
        .thenMany(employeeRepository.findAllIndirectAndDirectReportees(employeeId))
        .collectList()
        .map(ResponseEntity::ok);
  }

  @GetMapping("/{tenant}/api/employee/{id}")
  public Mono<ResponseEntity> getEmployeeDetails(@PathVariable("id") Long employeeId) {
    return employeeRepository.getDetails(employeeId)
        .map(ResponseEntity::ok);
  }

  private Mono<Employee> validateReportsTo(Employee employee, Long employeeId) {
    if (employee.getReportsTo() != null && employee.getReportsTo().equals(employeeId)) { //cant report to self
      return Mono.error(new SelfReportingException());
    } else {
      return validateReportsTo(employee);
    }
  }

  private Mono<Employee> validateReportsTo(Employee employee) {
    if (employee.getReportsTo() == null) {
      return employeeRepository.findByReportsTo(null).collectList()
          .handle((employees, sink) -> {
            if (employees.size() != 0) {
              sink.error(new MultipleRootEmployeeException());
            } else {
              sink.next(employee);
            }
          });
    } else {
      return validateManagerExists(employee);
    }
  }

  private Mono<Employee> validateManagerExists(Employee employee) {
    if (employee.getReportsTo() != null) {
      return employeeRepository.findById(employee.getReportsTo())
          .switchIfEmpty(Mono.error(new ManagerNotPresentException(employee.getReportsTo(), employee.getName())))
          .then(Mono.just(employee));
    } else {
      return Mono.just(employee);
    }
  }

}

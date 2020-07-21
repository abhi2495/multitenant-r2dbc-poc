package com.example.persistence.translator;

import com.example.controller.contract.EmployeeRequestContract;
import com.example.persistence.entity.Employee;
import com.example.utils.BaseTranslator;

public class EmployeeTranslator extends BaseTranslator<EmployeeRequestContract, Employee> {
  @Override
  public Employee translateTo(EmployeeRequestContract employeeRequestContract) {
    if (employeeRequestContract == null) {
      return null;
    }
    Employee employee = new Employee();
    employee.setAge(employeeRequestContract.getAge());
    employee.setName(employeeRequestContract.getName());
    employee.setReportsTo(employeeRequestContract.getManagerId());
    employee.setGrade(employeeRequestContract.getGrade());
    return employee;
  }
}

package com.example.persistence.repository;

import com.example.controller.contract.EmployeeDetailsResponseContract;
import com.example.persistence.entity.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomEmployeeRepository {
  Flux<Employee> findAllIndirectAndDirectReportees(Long managerId);

  Mono<EmployeeDetailsResponseContract> getDetails(Long employeeId);
}

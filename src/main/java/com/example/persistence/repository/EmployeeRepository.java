package com.example.persistence.repository;

import com.example.persistence.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long>, CustomEmployeeRepository {
  Flux<Employee> findByReportsTo(Long managerId);

}

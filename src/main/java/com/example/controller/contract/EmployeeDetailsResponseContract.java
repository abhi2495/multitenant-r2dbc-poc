package com.example.controller.contract;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class EmployeeDetailsResponseContract {

  private Long id;

  private String name;

  private int age;

  private Long reportsTo;

  private String grade;

  private int salary;

  private String role;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Long getReportsTo() {
    return reportsTo;
  }

  public void setReportsTo(Long reportsTo) {
    this.reportsTo = reportsTo;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

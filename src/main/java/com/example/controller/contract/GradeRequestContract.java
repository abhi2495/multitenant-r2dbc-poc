package com.example.controller.contract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GradeRequestContract {

  @NotBlank(message = "Grade id cannot be blank or null")
  private String id;

  @NotNull(message = "Salary cannot be null")
  @Min(value = 1000, message = "Salary cannot be less than 1000")
  private Integer salary;

  @NotBlank(message = "Role cannot be blank or null")
  private String role;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getSalary() {
    return salary;
  }

  public void setSalary(Integer salary) {
    this.salary = salary;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

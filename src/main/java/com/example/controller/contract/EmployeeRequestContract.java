package com.example.controller.contract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EmployeeRequestContract implements Serializable {

  @NotBlank(message = "Employee name cannot be null or blank")
  private String name;

  @Min(value = 20, message = "Employee age cannot be lesser than 20")
  @NotNull(message = "Employee age cannot be null")
  private Integer age;

  private Long managerId;

  @NotBlank(message = "Employee grade cannot be null or blank")
  private String grade;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Long getManagerId() {
    return managerId;
  }

  public void setManagerId(Long managerId) {
    this.managerId = managerId;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }
}

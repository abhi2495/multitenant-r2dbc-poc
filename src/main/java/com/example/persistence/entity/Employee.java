package com.example.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("employee")
public class Employee {

  @Id
  @Column("id")
  private Long id;

  @Column("name")
  private String name;

  @Column("age")
  private int age;

  @Column("reports_to")
  private Long reportsTo;

  @Column("grade")
  private String grade;

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
}

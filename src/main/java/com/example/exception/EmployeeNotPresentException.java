package com.example.exception;

public class EmployeeNotPresentException extends RuntimeException {
  public EmployeeNotPresentException(){
    super("Employee not found in database");
  }
}

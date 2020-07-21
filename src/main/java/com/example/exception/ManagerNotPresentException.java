package com.example.exception;

public class ManagerNotPresentException extends RuntimeException {
  public ManagerNotPresentException(Long managerId, String employeeName){
    super("No employee found in the database with id: " + managerId + " whom " + employeeName + " can report to." );
  }
}

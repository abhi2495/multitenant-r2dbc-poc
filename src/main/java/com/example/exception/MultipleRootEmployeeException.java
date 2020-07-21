package com.example.exception;

public class MultipleRootEmployeeException extends RuntimeException {
  public MultipleRootEmployeeException(){
    super("Only 1 root employee (with no manager) can exist in the system.");
  }
}

package com.example.exception;

public class GradeNotPresentException extends RuntimeException {
  public GradeNotPresentException(){
    super("Grade not found in database");
  }
}

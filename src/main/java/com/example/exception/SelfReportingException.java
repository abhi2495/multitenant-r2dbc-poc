package com.example.exception;

public class SelfReportingException extends RuntimeException {
  public SelfReportingException(){
    super("An employee cannot report to self");
  }
}

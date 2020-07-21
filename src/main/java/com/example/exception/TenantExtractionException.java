package com.example.exception;

public class TenantExtractionException extends RuntimeException{
  public TenantExtractionException() {
    super("Could not extract tenant from request path");
  }
}

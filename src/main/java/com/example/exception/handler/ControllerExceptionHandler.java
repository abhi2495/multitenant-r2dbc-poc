package com.example.exception.handler;

import com.example.exception.EmployeeNotPresentException;
import com.example.exception.GradeNotPresentException;
import com.example.exception.ManagerNotPresentException;
import com.example.exception.MultipleRootEmployeeException;
import com.example.exception.SelfReportingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice //doesnt work for exceptions thrown from webfilters
public class ControllerExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity handleWebExchangeBindException(WebExchangeBindException ex) {
    List<String> errors = ex.getBindingResult().getAllErrors()
        .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    String response = String.join(", ", errors);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler({ManagerNotPresentException.class, MultipleRootEmployeeException.class,
      EmployeeNotPresentException.class, SelfReportingException.class, GradeNotPresentException.class})
  public ResponseEntity handleOtherExceptions(Exception ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}

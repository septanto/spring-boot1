package com.mastering.spring.springboot.bean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> todoNotFound(TodoNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "New Exception Details");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> todoNotFound(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), "Some Exception Thrown");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

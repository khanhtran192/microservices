package com.example.orderdetailservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestExceptionHandler(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(badRequestException.getMessage());
    }
}

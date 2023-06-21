package com.example.orderservice.exception;

import feign.FeignException;

public class BadRequestException extends FeignException {
    public BadRequestException(String message) {
        super(400,message);
    }
}

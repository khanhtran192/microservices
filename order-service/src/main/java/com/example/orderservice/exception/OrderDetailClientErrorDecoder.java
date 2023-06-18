package com.example.orderservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class OrderDetailClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException("Bad request");
            default:
                return new RuntimeException("Error " + response.status() + " with reason " + response.reason());
        }
    }
}

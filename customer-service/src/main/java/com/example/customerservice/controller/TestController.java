package com.example.customerservice.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/customer")
    public ResponseEntity<?> test1(){
        return new ResponseEntity<>("customer service", HttpStatusCode.valueOf(200));
    }
}

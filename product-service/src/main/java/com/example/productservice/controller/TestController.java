package com.example.productservice.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/product")
    public ResponseEntity<?> test1(){
        return new ResponseEntity<>("product service", HttpStatusCode.valueOf(200));
    }
}

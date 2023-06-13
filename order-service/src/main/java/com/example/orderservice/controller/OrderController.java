package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/exists/{orderId}")
    @ResponseBody
    public Boolean isOrderExistsById(@PathVariable String orderId) {
        return orderService.isExistsOrderById(orderId);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}

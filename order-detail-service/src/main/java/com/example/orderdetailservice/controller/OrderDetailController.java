package com.example.orderdetailservice.controller;

import com.example.orderdetailservice.dto.OrderDetailRequest;
import com.example.orderdetailservice.dto.OrderDetailResponse;
import com.example.orderdetailservice.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public ResponseEntity getOrderDetailsByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailsByOrderId(orderId));
    }
    @GetMapping("/{orderId}/amount")
    public ResponseEntity getAmountByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(orderDetailService.getTotalAmountByOrderId(orderId));
    }
    @PostMapping("/{orderId}")
    public ResponseEntity createOrderDetailByOrderId(@PathVariable String orderId,
                                                     @RequestBody List<OrderDetailRequest> dto) {
        return ResponseEntity.ok(orderDetailService.saveOrderDetailsByOrderId(orderId, dto));
    }
}

package com.example.orderservice.client;

import com.example.orderservice.dto.OrderDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ORDER-DETAIL-SERVICE", path = "/api/v1/order-details")
public interface OrderDetailClient {
    @GetMapping("/{orderId}")
    List<OrderDetailResponse> getOrderDetailsByOrderId(@PathVariable String orderId);

}

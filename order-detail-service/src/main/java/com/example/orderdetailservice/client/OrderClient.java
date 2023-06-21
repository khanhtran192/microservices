package com.example.orderdetailservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORDER-SERVICE", path = "/api/v1/orders")
public interface OrderClient {
    @GetMapping("/exists/{orderId}")
    Boolean isOrderExistsById(@PathVariable String orderId);
}

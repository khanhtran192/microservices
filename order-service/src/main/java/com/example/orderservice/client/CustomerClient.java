package com.example.orderservice.client;

import com.example.orderservice.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMER-SERVICE",path = "/api/v1/customers")
public interface CustomerClient {
    @GetMapping("/{id}")
    CustomerResponse getCustomerById(@PathVariable String id);
}

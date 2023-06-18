package com.example.orderdetailservice.client;

import com.example.orderdetailservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/v1/products")
public interface ProductClient {
    @GetMapping("/{productId}")
    ProductResponse getProductId(@PathVariable Long productId);
}

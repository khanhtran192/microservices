package com.example.orderservice.client;

import com.example.orderservice.config.OrderDetailClientConfig;
import com.example.orderservice.dto.OrderDetailRequest;
import com.example.orderservice.dto.OrderDetailResponse;
import com.example.orderservice.fallback.OrderDetailFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ORDER-DETAIL-SERVICE",
        path = "/api/v1/order-details",
        configuration = OrderDetailClientConfig.class,
        fallback = OrderDetailFallback.class)
public interface OrderDetailClient {
    @GetMapping("/{orderId}")
    List<OrderDetailResponse> getOrderDetailsByOrderId(@PathVariable String orderId);

    @GetMapping("/{orderId}/amount")
    Double getAmountByOrderId(@PathVariable String orderId);

    @PostMapping("/{orderId}")
    List<OrderDetailResponse> createOrderDetailByOrderId(@PathVariable String orderId,
                                                         @RequestBody List<OrderDetailRequest> orderDetails);
}

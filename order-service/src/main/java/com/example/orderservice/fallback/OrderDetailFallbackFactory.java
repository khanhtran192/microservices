package com.example.orderservice.fallback;

import com.example.orderservice.client.OrderDetailClient;
import com.example.orderservice.dto.OrderDetailRequest;
import com.example.orderservice.dto.OrderDetailResponse;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

public class OrderDetailFallbackFactory implements FallbackFactory<OrderDetailClient> {

    @Override
    public OrderDetailClient create(Throwable cause) {
        return null;
    }
}

package com.example.orderservice.fallback;

import com.example.orderservice.client.OrderDetailClient;
import com.example.orderservice.dto.OrderDetailRequest;
import com.example.orderservice.dto.OrderDetailResponse;

import java.util.List;

public class OrderDetailFallback implements OrderDetailClient {
    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId) {
        return null;
    }

    @Override
    public Double getAmountByOrderId(String orderId) {
        return 0D;
    }

    @Override
    public List<OrderDetailResponse> createOrderDetailByOrderId(String orderId, List<OrderDetailRequest> orderDetails) {
        return null;
    }
}

package com.example.orderservice.service;

import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders();

    List<OrderResponse> getOrderByCustomerId(String customerId);

    OrderResponse getOrderById(String orderId);

    OrderResponse updateStatusOrder(OrderStatus orderStatus);

    boolean isExistsOrderById(String orderId);
}

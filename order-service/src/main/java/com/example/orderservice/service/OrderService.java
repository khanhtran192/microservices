package com.example.orderservice.service;

import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders();

    OrderResponse getOrderById(String orderId);

    OrderResponse updateStatusOrder(OrderStatus orderStatus);

    boolean isExistsOrderById(String orderId);

    OrderResponse createOrder(OrderRequest dto);
}

package com.example.orderservice.service.impl;

import com.example.orderservice.client.OrderDetailClient;
import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.dto.OrderDetailResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repo.OrderRepo;
import com.example.orderservice.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderDetailClient orderDetailClient;

    @PostConstruct
    public void initData() {
        List<Order> orders = List.of(
                Order.builder()
                        .id("Order-1")
                        .customerId("Customer-01")
                        .status(OrderStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Order.builder()
                        .id("Order-2")
                        .customerId("Customer-01")
                        .status(OrderStatus.CANCEL)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Order.builder()
                        .id("Order-3")
                        .customerId("Customer-03")
                        .status(OrderStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Order.builder()
                        .id("Order-4")
                        .customerId("Customer-02")
                        .status(OrderStatus.COMPLETED)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Order.builder()
                        .id("Order-5")
                        .customerId("Customer-04")
                        .status(OrderStatus.UNPAID)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Order.builder()
                        .id("Order-6")
                        .customerId("Customer-01")
                        .status(OrderStatus.COMPLETED)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        orderRepo.saveAll(orders);
    }

    @Override
    public List<OrderResponse> getOrders() {
        return null;
    }

    @Override
    public List<OrderResponse> getOrderByCustomerId(String customerId) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepo.findById(orderId).get();
        if (order == null) {
            return null;
        }
        List<OrderDetailResponse> orderDetails = orderDetailClient.getOrderDetailsByOrderId(orderId);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderDetails(orderDetails);
        orderResponse.setOrderId(orderId);
        orderResponse.setOrderStatus(order.getStatus().name());
        Double totalAmount = 0D;
        for (OrderDetailResponse orderDetail : orderDetails) {
            totalAmount += orderDetail.getProductPrice() * orderDetail.getProductQuantity();
        }
        orderResponse.setTotalAmount(totalAmount);
        return orderResponse;
    }

    @Override
    public OrderResponse updateStatusOrder(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public boolean isExistsOrderById(String orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isPresent()) {
            return true;
        }
        return false;
    }
}

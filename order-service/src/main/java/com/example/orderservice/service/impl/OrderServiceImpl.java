package com.example.orderservice.service.impl;

import com.example.orderservice.client.CustomerClient;
import com.example.orderservice.client.OrderDetailClient;
import com.example.orderservice.constant.OrderStatus;
import com.example.orderservice.dto.CustomerResponse;
import com.example.orderservice.dto.OrderDetailResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.BadRequestException;
import com.example.orderservice.repo.OrderRepo;
import com.example.orderservice.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderDetailClient orderDetailClient;
    @Autowired
    private CustomerClient customerClient;

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
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> result = new ArrayList<>();
        for (Order order : orders) {
            CustomerResponse customer = customerClient.getCustomerById(order.getCustomerId());
            Double totalAmount = orderDetailClient.getAmountByOrderId(order.getId());
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            orderResponse.setOrderStatus(order.getStatus().name());
            orderResponse.setCustomerName(String.format("%s %s", customer.getFirstname(), customer.getSurname()));
            orderResponse.setCustomerAddress(customer.getAddress());
            orderResponse.setTotalAmount(totalAmount);
            result.add(orderResponse);
        }
        return result;
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepo.findById(orderId).get();
        if (order == null) {
            return null;
        }
        CustomerResponse customer = customerClient.getCustomerById(order.getCustomerId());
        List<OrderDetailResponse> orderDetails = orderDetailClient.getOrderDetailsByOrderId(orderId);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderDetails(orderDetails);
        orderResponse.setOrderId(orderId);
        orderResponse.setOrderStatus(order.getStatus().name());
        orderResponse.setCustomerName(String.format("%s %s", customer.getFirstname(), customer.getSurname()));
        orderResponse.setCustomerAddress(customer.getAddress());
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

    @Override
    public OrderResponse createOrder(OrderRequest dto) {
        CustomerResponse customer = customerClient.getCustomerById(dto.getCustomerId());
        if (ObjectUtils.isEmpty(customer)) {
            return null;
        }
        String orderId = "Order-" + RandomStringUtils.randomNumeric(3);
        Order order = Order.builder()
                .id(orderId)
                .status(OrderStatus.PENDING)
                .customerId(dto.getCustomerId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        order = orderRepo.save(order);
        List<OrderDetailResponse> orderDetails = orderDetailClient.createOrderDetailByOrderId(orderId, dto.getOrderDetails());
        if (CollectionUtils.isEmpty(orderDetails)) {
           orderRepo.delete(order);
           throw new BadRequestException("Order ....");
        }
        // TODO: 6/17/2023 : need check response
        AtomicReference<Double> totalAmount = new AtomicReference<>(0D);
        orderDetails.forEach(orderDetail -> totalAmount.updateAndGet(v -> v + orderDetail.getProductPrice() * orderDetail.getProductQuantity()));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderId(orderId);
        orderResponse.setOrderStatus(order.getStatus().name());
        orderResponse.setCustomerName(String.format("%s %s", customer.getFirstname(), customer.getSurname()));
        orderResponse.setCustomerAddress(customer.getAddress());
        orderResponse.setOrderDetails(orderDetails);
        orderResponse.setTotalAmount(totalAmount.get());
        return orderResponse;
    }
}

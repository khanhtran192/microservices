package com.example.orderdetailservice.service.impl;

import com.example.orderdetailservice.client.OrderClient;
import com.example.orderdetailservice.dto.OrderDetailResponse;
import com.example.orderdetailservice.entity.OrderDetail;
import com.example.orderdetailservice.repo.OrderDetailRepo;
import com.example.orderdetailservice.service.OrderDetailService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderClient orderClient;

    @PostConstruct
    public void initData() {
        List<OrderDetail> orderDetails = List.of(
                OrderDetail.builder()
                        .orderId("Order-1")
                        .productId(1L)
                        .productName("")
                        .productQuantity(1)
                        .productPrice(400.00)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-1")
                        .productId(2L)
                        .productName("")
                        .productPrice(400.00)
                        .productQuantity(2)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-2")
                        .productId(1L)
                        .productName("")
                        .productQuantity(1)
                        .productPrice(400.00)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-3")
                        .productId(1L)
                        .productName("")
                        .productPrice(400.00)
                        .productQuantity(2)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-3")
                        .productId(2L)
                        .productName("")
                        .productQuantity(2)
                        .productPrice(400.00)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-4")
                        .productId(3L)
                        .productName("")
                        .productPrice(400.00)
                        .productQuantity(1)
                        .build(), OrderDetail.builder()
                        .orderId("Order-1")
                        .productId(1L)
                        .productName("")
                        .productQuantity(1)
                        .productPrice(400.00)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-4")
                        .productId(4L)
                        .productName("")
                        .productPrice(400.00)
                        .productQuantity(2)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-5")
                        .productId(3L)
                        .productName("")
                        .productQuantity(1)
                        .productPrice(400.00)
                        .build(),
                OrderDetail.builder()
                        .orderId("Order-5")
                        .productId(4L)
                        .productName("")
                        .productPrice(400.00)
                        .productQuantity(2)
                        .build()
        );
        orderDetailRepo.saveAll(orderDetails);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId) {
        if (!orderClient.isOrderExistsById(orderId)) {
            return new ArrayList<>();
        }
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        return orderDetails
                .stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .collect(Collectors.toList());
    }
}

package com.example.orderdetailservice.service.impl;

import com.example.orderdetailservice.client.OrderClient;
import com.example.orderdetailservice.client.ProductClient;
import com.example.orderdetailservice.dto.OrderDetailRequest;
import com.example.orderdetailservice.dto.OrderDetailResponse;
import com.example.orderdetailservice.dto.ProductResponse;
import com.example.orderdetailservice.entity.OrderDetail;
import com.example.orderdetailservice.exception.BadRequestException;
import com.example.orderdetailservice.repo.OrderDetailRepo;
import com.example.orderdetailservice.service.OrderDetailService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

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

    @Override
    public List<OrderDetailResponse> saveOrderDetailsByOrderId(String orderId, List<OrderDetailRequest> orderDetails) {
        List<OrderDetailResponse> result = new ArrayList<>();
//        if (!orderClient.isOrderExistsById(orderId)) {
//            return result;
//        }
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new BadRequestException("Body is null");
//            throw new RuntimeException("asdasd");
        }
        for (OrderDetailRequest dto : orderDetails) {
            ProductResponse product = productClient.getProductId(dto.getProductId());
            if (Objects.isNull(product)) {
                throw new BadRequestException("Not found product with id " + dto.getProductId());
            }
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderId(orderId)
                    .productId(dto.getProductId())
                    .productPrice(product.getPrice())
                    .productName(product.getName())
                    .productQuantity(dto.getProductQuantity())
                    .build();
            orderDetail = orderDetailRepo.save(orderDetail);
            result.add(modelMapper.map(orderDetail, OrderDetailResponse.class));
        }
        return result;
    }

    @Override
    public Double getTotalAmountByOrderId(String orderId) {
        if (!orderClient.isOrderExistsById(orderId)) {
            return null;
        }
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        Double totalAmount = 0D;
        for (OrderDetail orderDetail : orderDetails) {
            totalAmount += orderDetail.getProductPrice() * orderDetail.getProductQuantity();
        }
        return totalAmount;
    }
}

package com.example.orderdetailservice.service;

import com.example.orderdetailservice.dto.OrderDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId);

}

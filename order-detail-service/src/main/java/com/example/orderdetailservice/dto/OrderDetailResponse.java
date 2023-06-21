package com.example.orderdetailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
}

package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();

    ProductResponse insertProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest);

    ProductResponse findById(Long id);
    List<ProductResponse> findByCustomerId(String customerId);
}

package com.example.productservice.service.impl;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.repo.ProductRepo;
import com.example.productservice.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @PostConstruct
    public void initData() {
        Long totalProducts = productRepo.countProducts();
        if (totalProducts > 0) {
            return;
        }
        List<Product> products = List.of(
                Product.builder()
                        .name("Iphone")
                        .price(400.00)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Product.builder()
                        .name("Samsung")
                        .price(400.00)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Product.builder()
                        .name("Oppo")
                        .price(400.00)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Product.builder()
                        .name("Vivo")
                        .price(400.00)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        productRepo.saveAll(products);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepo.findAll()
                .stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse insertProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        product = productRepo.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepo.findById(productId).get();
        if(Objects.isNull(product)){
            return new ProductResponse();
        }
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepo.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepo.findById(id).get();
        if(Objects.isNull(product)){
            return new ProductResponse();
        }
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> findByCustomerId(String customerId) {
        return null;
    }
}

package com.example.productservice.controller;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.service.ProductService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> insertProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity
                .status(HttpStatus.SC_CREATED)
                .body(productService.insertProduct(productRequest));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,
                                                         @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, productRequest));
    }


}

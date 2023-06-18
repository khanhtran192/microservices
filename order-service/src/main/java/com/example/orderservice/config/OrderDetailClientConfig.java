package com.example.orderservice.config;

import com.example.orderservice.exception.OrderDetailClientErrorDecoder;
import com.example.orderservice.fallback.OrderDetailFallback;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OrderDetailClientConfig {
    @Bean
    Logger.Level orderDetailClientLogger() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${order.username}") String username,
                                                 @Value("${order.password}") String password) {
        return requestTemplate -> {
            requestTemplate.header("username", username);
            requestTemplate.header("password", password);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new OrderDetailClientErrorDecoder();
    }
    @Bean
    public OrderDetailFallback orderDetailFallback(){
        return new OrderDetailFallback();
    }
}

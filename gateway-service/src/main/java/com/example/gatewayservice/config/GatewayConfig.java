package com.example.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;



@Configuration
@RestController
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product",r -> r.path("/product/**") // tất cả các URL sau /
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header") // set header
                                .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081/")) // URI đích mà request sẽ được chuyển đến
                .route("customer", r -> r
                        .path("/**")
                        .filters(f -> f.circuitBreaker(config -> config // filter chịu lỗi
                                .setName("test") // tên
                                .setFallbackUri("forward:/fallback"))) // uri đích dự phòng khi route ngỏm
                        .uri("http://localhost:8082/"))
                .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}

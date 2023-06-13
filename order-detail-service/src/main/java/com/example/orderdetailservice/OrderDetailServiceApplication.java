package com.example.orderdetailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = {"com.example.orderdetailservice.entity"})
@EnableJpaRepositories(basePackages = {"com.example.orderdetailservice.repo"})
@EnableTransactionManagement
public class OrderDetailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderDetailServiceApplication.class, args);
	}

}

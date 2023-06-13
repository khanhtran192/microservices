package com.example.customerservice.service;

import com.example.customerservice.dto.CustomerRequest;
import com.example.customerservice.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomers();

    CustomerResponse updateCustomer(String customerId, CustomerRequest dto);

    CustomerResponse findById(String id);
}

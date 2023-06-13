package com.example.customerservice.service.impl;

import com.example.customerservice.dto.CustomerRequest;
import com.example.customerservice.dto.CustomerResponse;
import com.example.customerservice.entity.Customer;
import com.example.customerservice.repo.CustomerRepo;
import com.example.customerservice.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void initData() {
        List<Customer> customers = List.of(
                Customer.builder()
                        .id("Customer-01")
                        .firstname("Nguyễn Văn")
                        .surname("A")
                        .email("nguyenvana@gmail.com")
                        .mobile("0987654321")
                        .address("Hà Nội")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Customer.builder()
                        .id("Customer-02")
                        .firstname("Nguyễn Văn")
                        .surname("B")
                        .email("nguyenvanb@gmail.com")
                        .mobile("0987654312")
                        .address("Hà Nội")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Customer.builder()
                        .id("Customer-03")
                        .firstname("Nguyễn Văn")
                        .surname("C")
                        .email("nguyenvanc@gmail.com")
                        .mobile("0987654213")
                        .address("Hà Nội")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Customer.builder()
                        .id("Customer-04")
                        .firstname("Nguyễn Văn")
                        .surname("D")
                        .email("nguyenvanb@gmail.com")
                        .mobile("0987652134")
                        .address("Hà Nội")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        customerRepo.saveAll(customers);
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(String customerId, CustomerRequest dto) {
        Customer customer = customerRepo.findById(customerId).get();
        if (Objects.isNull(customer)) {
            return new CustomerResponse();
        }
        customer.setFirstname(dto.getFirstname());
        customer.setSurname(dto.getSurname());
        customer.setEmail(dto.getEmail());
        customer.setMobile(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setUpdatedAt(LocalDateTime.now());
        customer = customerRepo.save(customer);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer = customerRepo.findById(id).get();
        if (Objects.isNull(customer)) {
            return new CustomerResponse();
        }
        return modelMapper.map(customer, CustomerResponse.class);
    }
}

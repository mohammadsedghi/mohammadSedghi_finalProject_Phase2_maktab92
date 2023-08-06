package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}

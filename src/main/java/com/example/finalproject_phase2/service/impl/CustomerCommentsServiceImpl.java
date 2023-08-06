package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommentsServiceImpl implements CustomerCommentsService {
    private final CustomerCommentsRepository customerCommentsRepository;
    @Autowired
    public CustomerCommentsServiceImpl(CustomerCommentsRepository customerCommentsRepository) {
        this.customerCommentsRepository = customerCommentsRepository;

    }
}

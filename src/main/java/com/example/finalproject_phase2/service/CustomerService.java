package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.*;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByEmail(String email);
    CustomerDto addCustomer(CustomerDto customerSignUpDto);
    String encryptCustomerPassword(String password);
    CustomerDto loginByEmailAndPassword(CustomerLoginDto customerLoginDto);
    boolean changePassword(CustomerChangePasswordDto customerChangePasswordDto);
}

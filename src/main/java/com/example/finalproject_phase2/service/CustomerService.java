package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByEmail(String email);
    CustomerDto addCustomer(CustomerDto customerSignUpDto);
    String encryptCustomerPassword(String password);
    CustomerDto loginByEmailAndPassword(CustomerLoginDto customerLoginDto);
//    boolean changePassword(CustomerChangePasswordDto customerChangePasswordDto);
    boolean changePassword(String email,String password );
    List<Customer> searchCustomer(CustomerDto customerDto);
    AuthenticationResponse register(Customer customer);
    AuthenticationResponse authenticate(CustomerLoginDto customerLoginDto);
}

package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.*;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;

public interface CustomerService {
    ProjectResponse addCustomer(CustomerSignUpDto customerSignUpDto);
    String encryptCustomerPassword(String password);
    ProjectResponse loginByEmailAndPassword(CustomerLoginDto customerLoginDto);
    ProjectResponse changePassword(CustomerChangePasswordDto customerChangePasswordDto);
}

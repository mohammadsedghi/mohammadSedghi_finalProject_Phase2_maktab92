package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }


    @PostMapping("/signUp")
    public ResponseEntity<CustomerDto> AddCustomer(@RequestBody @Valid CustomerDto customerDto) {
        CustomerDto customerDtoResult = customerService.addCustomer(customerDto);
        if (customerDtoResult!=null){
            return new ResponseEntity<>(customerDtoResult, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> loginByEmailAndPassword(@RequestBody @Valid CustomerLoginDto customerLoginDto) {
        CustomerDto customerDto = customerService.loginByEmailAndPassword(customerLoginDto);
        if (customerDto!=null){
            return new ResponseEntity<>(customerDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid  CustomerChangePasswordDto customerChangePasswordDto) {
       if(customerService.changePassword(customerChangePasswordDto)) {
           return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
       }else throw new CustomNoResultException("password not changed");
    }


}
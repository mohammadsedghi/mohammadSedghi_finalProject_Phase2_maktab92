package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.service.CustomerService;
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

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signUpCustomer")
    public ResponseEntity<ProjectResponse> AddCustomer(@RequestBody CustomerSignUpDto customerSignUpDto) {

        ProjectResponse projectResponse = customerService.addCustomer(customerSignUpDto);
        if (projectResponse.getCode().equals("200")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.ACCEPTED);
        }
        if (projectResponse.getCode().equals("500")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(projectResponse, HttpStatus.SERVICE_UNAVAILABLE);

    }

    @PostMapping("/loginCustomer")
    public ResponseEntity<ProjectResponse> loginByEmailAndPassword(@RequestBody CustomerLoginDto customerLoginDto) {
        ProjectResponse projectResponse = customerService.loginByEmailAndPassword(customerLoginDto);
        if (projectResponse.getCode().equals("200")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.ACCEPTED);
        }
        if (projectResponse.getCode().equals("500")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(projectResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @PostMapping("/changePasswordCustomer")
    public ResponseEntity<ProjectResponse> changePassword(@RequestBody CustomerChangePasswordDto customerChangePasswordDto) {
        ProjectResponse projectResponse = customerService.changePassword(customerChangePasswordDto);
        if (projectResponse.getCode().equals("200")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.ACCEPTED);
        }
        if (projectResponse.getCode().equals("500")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(projectResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
package com.example.finalproject_phase2.controller.security_config;

import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello(){
        return  ResponseEntity.ok("hello");
    }
    @GetMapping("/way")
    public ResponseEntity<String> sayBy(){
        return  ResponseEntity.ok("by");
    }


}

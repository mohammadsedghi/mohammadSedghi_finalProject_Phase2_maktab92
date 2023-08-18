package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.impl.mapper.AdminMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private  final AdminService adminService;
    private final AdminMapper adminMapper;
@Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }
    @PostMapping("/login")
    public ResponseEntity<AdminDto> loginByEmailAndPassword(@RequestBody @Valid AdminLoginDto adminLoginDto) {
        AdminDto adminDto = adminService.loginByEmailAndPassword(adminLoginDto);
        if (adminDto!=null){
            return new ResponseEntity<>(adminDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }
}

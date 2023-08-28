package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.controller.security_config.AuthenticationResponse;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.entity.Admin;

import java.util.Optional;

public interface AdminService {
    AdminDto loginByEmailAndPassword(AdminLoginDto adminLoginDto);
    String encryptAdminPassword(String password);
    Optional<Admin> findByEmail(String email);
     AuthenticationResponse register(Admin admin);
     AuthenticationResponse authenticate(AdminLoginDto  adminLoginDto);
}

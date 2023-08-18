package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;

public interface AdminService {
    AdminDto loginByEmailAndPassword(AdminLoginDto adminLoginDto);
    String encryptAdminPassword(String password);
}

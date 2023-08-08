package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
   private final AdminRepository adminRepository;
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}

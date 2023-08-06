package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.service.AddressService;

public class AdminServiceImpl implements AddressService {
   private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}

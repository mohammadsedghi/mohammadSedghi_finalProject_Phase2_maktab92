package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
   private final AdminRepository adminRepository;
   CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public ProjectResponse loginByEmailAndPassword(AdminLoginDto adminLoginDto) {
        try {
            if (checkValidation.isValidEmail(adminLoginDto.getEmail()) && checkValidation.isValidPassword(adminLoginDto.getPassword())) {
                adminRepository.findByEmailAndPassword(adminLoginDto.getEmail(), encryptAdminPassword(adminLoginDto.getPassword())).ifPresentOrElse(
                        admin -> {
                            CheckValidation.memberTypeAdmin = admin;
                        }
                        , () -> {
                            throw new CustomNoResultException("customer not found");
                        }
                );
            } else {
                throw new CustomNoResultException("you inter invalid input for login");
            }
        } catch (CustomNoResultException c) {
            CheckValidation.memberTypeCustomer = new Customer();
            return new ProjectResponse("500", c.getMessage());
        }
        return new ProjectResponse("202", "customer accepted");
    }

    @Override
    public String encryptAdminPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }
}

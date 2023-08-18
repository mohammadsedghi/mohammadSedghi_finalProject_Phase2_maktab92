package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.impl.mapper.AdminMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
   private final AdminRepository adminRepository;
   private final AdminMapper adminMapper;
   CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminDto loginByEmailAndPassword(AdminLoginDto adminLoginDto) {
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
        } catch (CustomNoResultException cre) {
            CheckValidation.memberTypeAdmin = new Admin();
            return new AdminDto();
        }
        return adminMapper.adminToAdminDto(CheckValidation.memberTypeAdmin) ;
    }

    @Override
    public String encryptAdminPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }
}

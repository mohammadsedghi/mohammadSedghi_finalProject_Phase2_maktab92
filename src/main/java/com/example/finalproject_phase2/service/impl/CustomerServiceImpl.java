package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.WalletService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletService walletService;
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, WalletService walletService) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
    }


    public Optional<Customer> findByEmail(String email) {
        try {
            return customerRepository.findByEmail(email);
        } catch (CustomException e) {
            throw new CustomException("transaction error");
        }
    }

    @Transactional
    @Override
    public ProjectResponse addCustomer(CustomerSignUpDto customerSignUpDto) {
        try {
            if (!checkValidation.isValid(customerSignUpDto)) throw new CustomException("input is invalid ");
            findByEmail(customerSignUpDto.getEmail()).ifPresentOrElse(
                    tempCustomer -> {
                        throw new CustomException("customer with this email and password is exist ");

                    }, () -> {
                        Customer customer = CustomerMapper.customerDtoToCustomer(customerSignUpDto);
                        customer.setPassword(encryptCustomerPassword(customer.getPassword()));
                        customer.setRegisterDate(LocalDate.now());
                        customer.setRegisterTime(LocalTime.now());
//                          Wallet wallet=new Wallet(0d);
                        customer.setWallet(walletService.createWallet());
                        customerRepository.save(customer);
                    });
            return new ProjectResponse("201", "customer saved(created)");
        } catch (CustomException c) {
            return new ProjectResponse("500", c.getMessage());
        }

    }

    @Override
    public String encryptCustomerPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }

    @Override
    public ProjectResponse loginByEmailAndPassword(CustomerLoginDto customerLoginDto) {
        try {
            if (checkValidation.isValidEmail(customerLoginDto.getEmail()) && checkValidation.isValidPassword(customerLoginDto.getPassword())) {
                customerRepository.findByEmailAndPassword(customerLoginDto.getEmail(), encryptCustomerPassword(customerLoginDto.getPassword())).ifPresentOrElse(
                        customer -> {
                            CheckValidation.memberTypeCustomer = customer;
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
    public ProjectResponse changePassword(CustomerChangePasswordDto customerChangePasswordDto) {
        try {
            if (checkValidation.isValidEmail(customerChangePasswordDto.getEmail()) && checkValidation.isValidPassword(customerChangePasswordDto.getOldPassword())) {
                if (checkValidation.isValidPassword(customerChangePasswordDto.getNewPassword())) {
                    customerRepository.findByEmailAndPassword(customerChangePasswordDto.getEmail(), encryptCustomerPassword(customerChangePasswordDto.getOldPassword())).ifPresentOrElse(
                            customer -> {
                                customer.setPassword(encryptCustomerPassword(customerChangePasswordDto.getNewPassword()));
                                    customerRepository.save(customer);
                            }, () -> {
                                throw new CustomNoResultException("this customer is not found");
                            }
                    );
                } else {
                    throw new CustomNoResultException("new password is invalid");
                }
            } else {
                throw new CustomNoResultException("email and old password is invalid");
            }
        }catch (CustomNoResultException c){
            return new ProjectResponse("500", c.getMessage());
        }
        return new ProjectResponse("202", "password changed");
    }
}
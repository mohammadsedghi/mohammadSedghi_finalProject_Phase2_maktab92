package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
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
    private final CustomerMapper customerMapper;
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, WalletService walletService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
        this.customerMapper = customerMapper;
    }

@Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return customerRepository.findByEmail(email);
        } catch (CustomException e) {
            throw new CustomException("transaction error");
        }
    }

    @Transactional
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        try {
            if (!checkValidation.isValid(customerDto)) throw new CustomException("input is invalid ");
            findByEmail(customerDto.getEmail()).ifPresentOrElse(
                    tempCustomer -> {
                        throw new CustomException("customer with this email and password is exist ");

                    }, () -> {
                        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
                        customer.setPassword(encryptCustomerPassword(customer.getPassword()));
                        customer.setRegisterDate(LocalDate.now());
                        customer.setRegisterTime(LocalTime.now());
                        customer.setWallet(walletService.createWallet());
                        customerRepository.save(customer);
                    });
            return  customerDto;
        } catch (CustomException c) {
            return new CustomerDto();
        }
    }

    @Override
    public String encryptCustomerPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }

    @Override
    public CustomerDto loginByEmailAndPassword(CustomerLoginDto customerLoginDto) {
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
            return new CustomerDto();
        }
        return customerMapper.customerToCustomerDto(CheckValidation.memberTypeCustomer);
    }

    @Override
    public boolean changePassword(CustomerChangePasswordDto customerChangePasswordDto) {
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
            return false;
        }
        return true;
    }
}
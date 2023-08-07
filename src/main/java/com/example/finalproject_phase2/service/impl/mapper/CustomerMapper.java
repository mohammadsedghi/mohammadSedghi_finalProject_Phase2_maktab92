package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerResult;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public static Customer customerDtoToCustomer(CustomerSignUpDto customerSignUpDto) {
        return Customer.builder()
                .firstName(customerSignUpDto.getFirstName())
                .lastname(customerSignUpDto.getLastName())
                .nationalId(customerSignUpDto.getNationalId())
                .email(customerSignUpDto.getEmail())
                .password(customerSignUpDto.getPassword())
                .build();
    }
    public static CustomerResult customerToCustomerResult(Customer customer){
        return CustomerResult.builder()
                .firstName(customer.getFirstName())
                .lastname(customer.getLastName())
                .nationalId(customer.getNationalId())
                .registerDate(customer.getRegisterDate())
                .registerTime(customer.getRegisterTime())
                .email(customer.getEmail())
                .build();
    }
    public static Customer customerChangePasswordDtoToCustomer(CustomerChangePasswordDto customerChangePasswordDto) {
        return Customer.builder()
                .email(customerChangePasswordDto.getEmail())
                .password(customerChangePasswordDto.getNewPassword())
                .build();
    }
}

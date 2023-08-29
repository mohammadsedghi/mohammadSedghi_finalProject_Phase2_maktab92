package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.controller.security_config.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }
    @PostMapping("/signUp")
    public ResponseEntity<CustomerDto> AddCustomer(@RequestBody @Valid CustomerDto customerDto) {
            CustomerDto customerDtoResult = customerService.addCustomer(customerDto);
             if (customerDtoResult!=null){
            return new ResponseEntity<>(customerDtoResult, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> loginByEmailAndPassword(@RequestBody @Valid CustomerLoginDto customerLoginDto) {
        CustomerDto customerDto = customerService.loginByEmailAndPassword(customerLoginDto);
        if (customerDto!=null){
            return new ResponseEntity<>(customerDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("customer not saved");
    }

//    @PostMapping("/changePassword")
//    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid  CustomerChangePasswordDto customerChangePasswordDto) {
//       if(customerService.changePassword(customerChangePasswordDto)) {
//           return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
//       }else throw new CustomNoResultException("password not changed");
//    }
    @PostMapping("/search")
    public ResponseEntity<List<Customer>> searchSpecialist(@RequestBody CustomerDto customerDto) {
        List<Customer> customers = customerService.searchCustomer(customerDto);
        return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
    }
    @PostMapping("/registerCustomer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody CustomerDto customerDto
            , @RequestParam String userType){
        System.out.println(customerMapper.customerDtoToCustomer(customerDto).getEmail());
        CheckValidation.userType=userType;
        System.out.println(userType);
        if (userType.equals("customer")) {
            return ResponseEntity.ok(customerService.register(customerMapper.customerDtoToCustomer(customerDto)));
        }else  return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/authenticationCustomer")
    public ResponseEntity<AuthenticationResponse> loginCustomer(@RequestBody CustomerLoginDto customerLoginDto
            , @RequestParam String userType){
        CheckValidation.userType=userType;
        System.out.println(customerLoginDto.getEmail());
        System.out.println(userType);
        if (userType.equals("customer")){
            return  ResponseEntity.ok(customerService.authenticate(customerLoginDto));
        }else  return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid  CustomerChangePasswordDto customerChangePasswordDto) {

        if(customerService.changePassword(customerChangePasswordDto.getEmail(),customerChangePasswordDto.getNewPassword())) {
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }else throw new CustomNoResultException("password not changed");
    }
}
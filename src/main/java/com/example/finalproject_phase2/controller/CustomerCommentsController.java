package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("CustomerComments")
public class CustomerCommentsController {
    private final CustomerCommentsService customerCommentsService;

    @Autowired
    public CustomerCommentsController(CustomerCommentsService customerCommentsService) {
        this.customerCommentsService = customerCommentsService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Boolean> submitCustomerComments(@RequestBody @Valid CustomerCommentsDto customerCommentsDto) {
        customerCommentsService.submitCustomerCommentsService(customerCommentsDto);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}

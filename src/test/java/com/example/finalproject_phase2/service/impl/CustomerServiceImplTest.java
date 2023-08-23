package com.example.finalproject_phase2.service.impl;


import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;
    CustomerDto customerDto;
    MotherObject motherObject;
    CustomerLoginDto customerLoginDto;
    CustomerChangePasswordDto customerChangePasswordDto;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
        customerDto = new CustomerDto();
        customerLoginDto = new CustomerLoginDto();
        customerChangePasswordDto = new CustomerChangePasswordDto();
    }

    @Test
    @Order(1)
    void findByEmail() {
        // assertEquals(throw new CustomException("transaction error"),customerService.findByEmail(motherObject.getDuplicateEmail());
        assertTrue(customerService.findByEmail(motherObject.getDuplicateEmail()).isPresent());


    }

    @Test
    @Order(2)
    void addCustomer() {
        assertEquals(motherObject.getValidCustomerSignUpDto(), customerService.addCustomer(motherObject.getValidCustomerSignUpDto()));
    }

    @Test
    @Order(3)
    void addCustomerInvalidInput() {
        assertEquals(motherObject.getInValidCustomerSignUpDto(), customerService.addCustomer(motherObject.getInValidCustomerSignUpDto()));
    }

    @Test
    @Order(4)
    void customerLoginByEmailAndPasswordTest() {
        assertEquals(motherObject.getValidCustomerLoginDto(), customerService.loginByEmailAndPassword(motherObject.getValidCustomerLoginDto()));
    }

    @Test
    @Order(5)
    void customerLoginByInvalidEmailAndInvalidPasswordTest() {
        assertEquals(motherObject.getInValidCustomerLoginDto(), customerService.loginByEmailAndPassword(motherObject.getInValidCustomerLoginDto()));
    }

    @Test
    @Order(6)
    void encryptCustomerPassword() {
        String hashPassword = "on0EK9kqkdhgaJdWgoJb4q34+hBF2c/hkzlRtljOIKoxXS+YoQwb0fxEp9WxAbAvPXqsOJKddFvzxFUQRbrxFQ==";
        assertEquals(hashPassword, customerService.encryptCustomerPassword(customerDto.getPassword()));
    }

    @Test
    @Order(7)
    void changePasswordTest() {
        customerService.changePassword(motherObject.getValidCustomerChangePasswordDto());
        customerService.loginByEmailAndPassword(motherObject.getValidCustomerLoginDtoAfterChangePassword());
        assertEquals(motherObject.getDuplicateEmail(),CheckValidation.memberTypeCustomer.getEmail());
    }
    @Test
    @Order(8)
    void searchCustomer(){
        CustomerDto customerDto=new CustomerDto();
        customerDto.setEmail("mahan@gmail.com");
        customerDto.setFirstName("reza");
        customerDto.setLastName("akbar");
        customerDto.setNationalId("4560116815");
        List<Customer> customers =customerService.searchCustomer(customerDto);
        System.out.println("cccccccccc"+customers.size());
        for (Customer c:customers
             ) {
            System.out.println(c.getEmail());
        }
    }
}
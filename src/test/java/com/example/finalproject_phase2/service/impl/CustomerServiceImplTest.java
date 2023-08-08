package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;
    CustomerSignUpDto customerSignUpDto;
    MotherObject motherObject;
    CustomerRepository customerRepository;
    CustomerLoginDto customerLoginDto;
    CustomerChangePasswordDto customerChangePasswordDto;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
        customerSignUpDto = new CustomerSignUpDto();
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
        assertEquals("202", customerService.addCustomer(motherObject.getValidCustomerSignUpDto()).getCode());
    }

    @Test
    @Order(3)
    void addCustomerInvalidInput() {
        assertEquals("500", customerService.addCustomer(motherObject.getInValidCustomerSignUpDto()).getCode());
    }

    @Test
    @Order(4)
    void customerLoginByEmailAndPasswordTest() {
        assertEquals("202", customerService.loginByEmailAndPassword(motherObject.getValidCustomerLoginDto()).getCode());
    }

    @Test
    @Order(5)
    void customerLoginByInvalidEmailAndInvalidPasswordTest() {
        assertEquals("500", customerService.loginByEmailAndPassword(motherObject.getInValidCustomerLoginDto()).getCode());
    }

    @Test
    @Order(6)
    void encryptCustomerPassword() {
        String hashPassword = "on0EK9kqkdhgaJdWgoJb4q34+hBF2c/hkzlRtljOIKoxXS+YoQwb0fxEp9WxAbAvPXqsOJKddFvzxFUQRbrxFQ==";
        assertEquals(hashPassword, customerService.encryptCustomerPassword(customerSignUpDto.getPassword()));
    }

    @Test
    @Order(7)
    void changePasswordTest() {
        customerService.changePassword(motherObject.getValidCustomerChangePasswordDto());
        customerService.loginByEmailAndPassword(motherObject.getValidCustomerLoginDtoAfterChangePassword());
        assertEquals(motherObject.getDuplicateEmail(),CheckValidation.memberTypeCustomer.getEmail());
    }
}
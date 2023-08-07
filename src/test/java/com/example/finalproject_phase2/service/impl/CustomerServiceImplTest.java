package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
//@ActiveProfiles("test")
class CustomerServiceImplTest {
    @InjectMocks
    CustomerService customerService;
    //   @InjectMocks
//    WalletService walletService;
    @Mock
    ProjectResponse projectResponse;
    @Mock
    CustomerSignUpDto customerSignUpDto;
    @Mock
    CustomerRepository customerRepository;
//    @Mock
//    CustomerSignUpDto customerSignUpDto;
    //@InjectMocks
//    ProjectResponse projectResponse = new ProjectResponse();
//    @InjectMocks
//    Customer customer;
//    private CustomerRepository repository;

//@Mock
    // private CustomerRepository customerRepository;

    //private CustomerService customerService;

    // private WalletService walletService;
    @BeforeEach
    void setUp() {
        customerService = Mockito.mock(CustomerServiceImpl.class);
       projectResponse = Mockito.mock(ProjectResponse.class);
       customerSignUpDto = Mockito.mock(CustomerSignUpDto.class);
       MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void addCustomer() {
        customerSignUpDto.setFirstName("mahan");
        customerSignUpDto.setLastName("se");
        customerSignUpDto.setNationalId("4560116814");
        customerSignUpDto.setEmail("mahan@gmail.com");
        customerSignUpDto.setPassword("123456mn");
        Mockito.when(customerService.addCustomer(customerSignUpDto)).thenReturn(projectResponse);
        Mockito.when(customerService.addCustomer(customerSignUpDto)).thenReturn(projectResponse);
        assertEquals(projectResponse, customerService.addCustomer(customerSignUpDto));

    }

    //        assertEquals("200", customerService.addCustomer(customerSignUpDto).getCode());
    @Test
    void encryptCustomerPassword() {
    }
}
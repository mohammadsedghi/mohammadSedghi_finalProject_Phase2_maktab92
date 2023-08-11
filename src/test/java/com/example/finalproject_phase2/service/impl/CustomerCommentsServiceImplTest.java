package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerCommentsServiceImplTest {
    @Autowired
    CustomerCommentsService customerCommentsService;
    @Autowired
    OrdersService ordersService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void submitCustomerCommentsService() {
        Optional<Orders> order = ordersService.findById(1l);
        CustomerComments customerComments= CustomerComments.builder()
                .orders(order.get())
                .description("customerComments")
                .score(3)
                .build();
 assertEquals("202",customerCommentsService.submitCustomerCommentsService(customerComments).getCode());
    }
}
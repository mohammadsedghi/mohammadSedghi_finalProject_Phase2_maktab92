package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrdersServiceImplTest {
    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    AddressService addressService;
    MotherObject motherObject;
    @BeforeEach
    void setUp() {
        motherObject=new MotherObject();
    }

    @Test
    void submitOrder() {
        Optional<Customer> customer = customerService.findByEmail("mahan@gmail.com");
        Specialist specialist = specialistService.findByEmail("ali@gmail.com");
        SubDuty subDuty = subDutyService.findByName("CD");
        Address address = addressService.createAddress(motherObject.getValidAddressDto());
//        Orders orders=new Orders(customer.get(),specialist,subDuty,13d,"orderrrrr",
//                LocalDate.now(), LocalTime.now(),address, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        Orders orders = Orders.builder()
                .customer(customer.get())
                .subDuty(subDuty)
                .description("order")
                .address(address)
                .DateOfWork(LocalDate.now())
                .timeOfWork(LocalTime.now())
                .orderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION)
                .build();
        ordersService.submitOrder(orders,"600",String.valueOf(subDuty.getBasePrice()));
    }

    @Test
    void showOrdersToSpecialist() {
    }

    @Test
    void updateOrderToNextLevel() {
    }

    @Test
    void findOrdersWithThisCustomerAndSubDuty() {
    }

    @Test
    void findOrdersInStatusWaitingForSpecialistSuggestion() {
    }

    @Test
    void findOrdersInStatusWaitingForSpecialistSelection() {
    }

    @Test
    void findOrdersInStatusWaitingForSpecialistToWorkplace() {
    }

    @Test
    void findOrdersInStatusStarted() {
    }

    @Test
    void findOrdersInStatusPaid() {
    }

    @Test
    void findOrdersInStatusDone() {
    }
}
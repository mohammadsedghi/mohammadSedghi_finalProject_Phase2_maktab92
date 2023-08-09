package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    @Order(1)
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
    @Order(2)
    void showOrdersToSpecialist() {
        SubDuty subDuty = subDutyService.findByName("CD");
        List<Orders> orders=new ArrayList<>(ordersService.showOrdersToSpecialist(subDuty));
        assertEquals(subDuty,orders.get(0).getSubDuty());
    }

    @Test
    @Order(3)
    void updateOrderToNextLevel() {
        OrderStatus orderStatus = ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION).getOrderStatus();
        assertEquals(ordersService.findById(1l).get().getOrderStatus(),orderStatus);
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION).getOrderStatus();

    }

    @Test
    @Order(4)
    void findOrdersWithThisCustomerAndSubDuty() {
        assertEquals("500",ordersService.findOrdersWithThisCustomerAndSubDuty(customerService.findByEmail("mahan@gmail.com").get(),
                subDutyService.findByName("CD")).getCode());
    }

    @Test
    @Order(5)
    void findOrdersInStatusWaitingForSpecialistSuggestion(){
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customer));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(6)
    void findOrdersInStatusWaitingForSpecialistSelection() {
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSelection(customer));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(7)
    void findOrdersInStatusWaitingForSpecialistToWorkplace() {
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customer));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(8)
    void findOrdersInStatusStarted() {
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_STARTED);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusStarted(customer));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(9)
    void findOrdersInStatusPaid() {
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_PAID);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusPaid(customer));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(10)
    void findOrdersInStatusDone() {
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_DONE);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusDone(customer));
        assertEquals(customer,orders.get(0).getCustomer());
        ordersService.updateOrderToNextLevel(ordersService.findById(1l).get(),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
    }
    @Test
    @Order(11)
    void findOrdersById() {
        ordersService.findById(1l);
    }
}
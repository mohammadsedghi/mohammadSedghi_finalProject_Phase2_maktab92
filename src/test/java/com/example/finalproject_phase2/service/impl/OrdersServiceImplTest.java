package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithCustomerAndSubDuty;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.ordersDto.OrdersWithPriceAndBasePrice;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
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
    CustomerMapper customerMapper;
    @Autowired
    SubDutyMapper subDutyMapper;
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
        SubDuty subDuty = subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD"));
        Address address = addressService.createAddress(motherObject.getValidAddressDto());
//        Orders orders=new Orders(customer.get(),specialist,subDuty,13d,"orderrrrr",
//     LocalDate.now(), LocalTime.now(),address, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        Orders orders = Orders.builder()
                .customer(customer.get())
                .subDuty(subDuty)
                .description("order")
                .address(address)
                .DateOfWork(LocalDate.now())
                .timeOfWork(LocalTime.now())
                .orderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION)
                .build();
        OrdersWithPriceAndBasePrice ordersWithPriceAndBasePrice = new OrdersWithPriceAndBasePrice();
        ordersWithPriceAndBasePrice.setOrders(orders);
        ordersWithPriceAndBasePrice.setPriceOfOrders("600");
        ordersService.submitOrder(ordersWithPriceAndBasePrice);
    }

    @Test
    @Order(2)
    void showOrdersToSpecialist() {
        SubDuty subDuty =subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD")) ;
        List<Orders> orders=new ArrayList<>(ordersService.showOrdersToSpecialist(subDutyMapper.subDutyToSubDutyDto(subDuty)));
        assertEquals(subDuty,orders.get(0).getSubDuty());
    }

    @Test
    @Order(3)
    void updateOrderToNextLevel() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        OrderStatus orderStatus = ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus).getOrderStatus();
        assertEquals(ordersService.findById(1l).get().getOrderStatus(),orderStatus);
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus1=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus1);

    }

    @Test
    @Order(4)
    void findOrdersWithThisCustomerAndSubDuty() {
        OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty=new OrdersDtoWithCustomerAndSubDuty();
        ordersDtoWithCustomerAndSubDuty.setCustomer(customerService.findByEmail("mahan@gmail.com").get());
        ordersDtoWithCustomerAndSubDuty.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD")));
        OrdersDto orderDto = ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty);
        assertEquals(orderDto,ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty));
    }

    @Test
    @Order(5)
    void findOrdersInStatusWaitingForSpecialistSuggestion(){
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(6)
    void findOrdersInStatusWaitingForSpecialistSelection() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSelection(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(7)
    void findOrdersInStatusWaitingForSpecialistToWorkplace() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(8)
    void findOrdersInStatusStarted() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_STARTED);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusStarted(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(9)
    void findOrdersInStatusPaid() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_PAID);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusPaid(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(10)
    void findOrdersInStatusDone() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(ordersService.findById(1l).get());
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_DONE);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusDone(customerMapper.customerToCustomerDto(customer)));
        assertEquals(customer,orders.get(0).getCustomer());
        ordersDtoWithOrdersStatus.setOrderStatus(  OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
    }
    @Test
    @Order(11)
    void findOrdersById() {
        ordersService.findById(1l);
    }
}
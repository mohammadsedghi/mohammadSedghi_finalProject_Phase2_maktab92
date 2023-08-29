package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithCustomerAndSubDuty;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.ordersDto.OrdersWithPriceAndBasePrice;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.impl.mapper.OrdersMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final OrdersMapper ordersMapper;
   @Autowired
    public OrdersController(OrdersService ordersService, OrdersMapper ordersMapper) {
        this.ordersService = ordersService;
       this.ordersMapper = ordersMapper;
   }


}

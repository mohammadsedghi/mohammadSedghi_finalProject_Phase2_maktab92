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

    @PostMapping("/submit")
    public ResponseEntity<OrdersDto> submitOrders(@RequestBody @Valid OrdersWithPriceAndBasePrice ordersWithPriceAndBasePrice ) {
        OrdersDto ordersDto = ordersService.submitOrder(ordersWithPriceAndBasePrice);
        if (ordersDto!=null){
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
        }else  throw new CustomException("orders not saved");
    }
    @PostMapping("/findOrdersWithThisCustomerAndSubDuty")
    public ResponseEntity<OrdersDto> findOrdersWithThisCustomerAndSubDuty(@RequestBody @Valid OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty ) {
        OrdersDto ordersDto = ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty);
        if (ordersDto!=null){
            return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
        }else throw new CustomException("orders not found");
    }
    @PostMapping("/showOrdersToSpecialist")
    public ResponseEntity<Collection<OrdersDto>> showOrdersToSpecialist(@RequestBody @Valid SubDutyDto subDutyDto ) {
        Collection<Orders> ordersCollection = ordersService.showOrdersToSpecialist(subDutyDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
            return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/updateOrderToNextLevelStatus")
    public ResponseEntity<OrdersDto> updateOrderToNextLevelStatus(@RequestBody @Valid OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus ) {
        OrdersDto ordersDto = ordersMapper.ordersToOrdersDto(ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus));
        return new ResponseEntity<>(ordersDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSuggestion")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSuggestion(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistSelection")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistSelection(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistSelection(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusWaitingForSpecialistToWorkplace")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusWaitingForSpecialistToWorkplace(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusStarted")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusStarted(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusStarted(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusPaid")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusPaid(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusPaid(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findOrdersInStatusDone")
    public ResponseEntity<Collection<OrdersDto>> findOrdersInStatusDone(@RequestBody @Valid CustomerDto customerDto ) {
        Collection<Orders> ordersCollection = ordersService.findOrdersInStatusDone(customerDto);
        Collection<OrdersDto> ordersDtoCollection = ordersMapper.collectionOrdersToCollectionOrdersDto(ordersCollection);
        return new ResponseEntity<>(ordersDtoCollection, HttpStatus.ACCEPTED);
    }
}

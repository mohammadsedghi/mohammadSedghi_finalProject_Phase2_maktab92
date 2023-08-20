package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithCustomerAndSubDuty;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.ordersDto.OrdersWithPriceAndBasePrice;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;

import java.util.Collection;
import java.util.Optional;

public interface OrdersService {
    OrdersDto submitOrder(OrdersWithPriceAndBasePrice ordersWithPriceAndBasePrice);
    Collection<Orders> showOrdersToSpecialist(SubDutyDto  subDutyDto );
    Orders updateOrderToNextLevel(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus);
    OrdersDto findOrdersWithThisCustomerAndSubDuty(OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty) ;
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(CustomerDto customerDto);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(CustomerDto customerDto);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(CustomerDto customerDto);
    Collection<Orders> findOrdersInStatusStarted(CustomerDto customerDto);
    Collection<Orders> findOrdersInStatusPaid(CustomerDto customerDto);
    Collection<Orders> findOrdersInStatusDone(CustomerDto customerDto);
    Optional<Orders> findById(Long id);
}

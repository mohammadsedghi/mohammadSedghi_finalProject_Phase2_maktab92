package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.entity.Orders;

public class OrdersMapper {
    public static Orders ordersDtoToOrders(OrdersDto ordersDto) {
        return Orders.builder().
                customer(ordersDto.getCustomer())
                .subDuty(ordersDto.getSubDuty())
                .address(ordersDto.getAddress())
                .description(ordersDto.getDescription())
                .proposedPrice(ordersDto.getProposedPrice())
                .orderStatus(ordersDto.getOrderStatus())
                .DateOfWork(ordersDto.getDateOfWork())
                .timeOfWork(ordersDto.getTimeOfWork()).
                build();
    }

    public static OrdersDto ordersToOrdersDto(Orders orders) {
        return OrdersDto.builder().
                customer(orders.getCustomer())
                .subDuty(orders.getSubDuty())
                .address(orders.getAddress())
                .description(orders.getDescription())
                .proposedPrice(orders.getProposedPrice())
                .orderStatus(orders.getOrderStatus())
                .dateOfWork(orders.getDateOfWork())
                .timeOfWork(orders.getTimeOfWork())
                        .build();
    }
}

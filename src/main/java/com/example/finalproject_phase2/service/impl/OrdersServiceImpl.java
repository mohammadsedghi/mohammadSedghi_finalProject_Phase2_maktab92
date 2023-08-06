package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.OrdersRepository;
import com.example.finalproject_phase2.service.OrdersService;

public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
}

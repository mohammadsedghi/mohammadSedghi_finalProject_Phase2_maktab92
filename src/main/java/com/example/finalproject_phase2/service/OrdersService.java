package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;

import java.util.Collection;
import java.util.Optional;

public interface OrdersService {
    ProjectResponse submitOrder(Orders orders, String priceOfOrder, String subDutyBasePrice);
    Collection<Orders> showOrdersToSpecialist(SubDuty subDuty );
    Orders updateOrderToNextLevel(Orders orders, OrderStatus orderStatus);
    ProjectResponse findOrdersWithThisCustomerAndSubDuty(Customer customer, SubDuty subDuty) ;
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(Customer customer);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(Customer customer);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(Customer customer);
    Collection<Orders> findOrdersInStatusStarted(Customer customer);
    Collection<Orders> findOrdersInStatusPaid(Customer customer);
    Collection<Orders> findOrdersInStatusDone(Customer customer);
    Optional<Orders> findById(Long id);
}

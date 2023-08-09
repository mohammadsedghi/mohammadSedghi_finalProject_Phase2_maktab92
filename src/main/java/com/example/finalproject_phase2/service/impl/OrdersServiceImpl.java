package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.repository.OrdersRepository;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
    @Override
    public ProjectResponse submitOrder(Orders orders, String priceOfOrder, String subDutyBasePrice) {
        CheckValidation checkValidation = new CheckValidation();
        CustomRegex customRegex = new CustomRegex();
        try {
            if (!customRegex.checkOneInputIsValid(priceOfOrder, customRegex.getValidPrice())) {
                throw new CustomException("input ProposedPrice for orders is invalid");
            } else {
                orders.setProposedPrice(Double.parseDouble(priceOfOrder) + Double.parseDouble(subDutyBasePrice));
            }
            if (!checkValidation.isValid(orders)) {
                throw new CustomException("input for orders is invalid");
            }
            if (!checkValidation.isValid(orders.getAddress())) {
                throw new CustomException("input address for orders is invalid");
            }
                ordersRepository.save(orders);
        } catch (CustomException ce) {
            return new ProjectResponse("500",ce.getMessage());
        }
        return new ProjectResponse("202","order submit");
    }

    @Override
    public Collection<Orders> showOrdersToSpecialist(SubDuty subDuty) {
        return ordersRepository.showOrdersToSpecialist(subDuty,OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION,OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
    }

    @Override
    public Orders updateOrderToNextLevel(Orders orders, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void findOrdersWithThisCustomerAndSubDuty(Customer customer, SubDuty subDuty) {

    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(Customer customer) {
        return null;
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(Customer customer) {
        return null;
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(Customer customer) {
        return null;
    }

    @Override
    public Collection<Orders> findOrdersInStatusStarted(Customer customer) {
        return null;
    }

    @Override
    public Collection<Orders> findOrdersInStatusPaid(Customer customer) {
        return null;
    }

    @Override
    public Collection<Orders> findOrdersInStatusDone(Customer customer) {
        return null;
    }
}

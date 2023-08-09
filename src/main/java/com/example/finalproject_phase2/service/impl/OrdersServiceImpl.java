package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomDuplicateInfoException;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
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

import java.util.*;

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
        orders.setOrderStatus(orderStatus);
        ordersRepository.save(orders);
        return orders;
    }
    @Override
    public ProjectResponse findOrdersWithThisCustomerAndSubDuty(Customer customer, SubDuty subDuty) {
        try {ordersRepository.findOrdersWithThisCustomerAndSubDuty(customer, subDuty,OrderStatus.ORDER_DONE).ifPresent(
                orders -> {
                        throw new CustomDuplicateInfoException("this customer submit order for this subDuty");
                    });

                }catch (CustomDuplicateInfoException cdi) {
            return new ProjectResponse("500",cdi.getMessage());
        }
        return new ProjectResponse("202","find Orders With This Customer And SubDuty");

    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(Customer customer) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION));
//        try {
//            if (customOrderSet.size() == 0) {
//                throw new CustomNoResultException("no order exist with this request");
//            }
//
//        } catch (CustomNoResultException cnr) {
//            System.out.println(cnr.getMessage());
//        }
        //return customOrderSet;

    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(Customer customer) {
         return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION));
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(Customer customer) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE));
    }

    @Override
    public Collection<Orders> findOrdersInStatusStarted(Customer customer) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_STARTED));
    }

    @Override
    public Collection<Orders> findOrdersInStatusPaid(Customer customer) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_PAID));
    }

    @Override
    public Collection<Orders> findOrdersInStatusDone(Customer customer) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customer,
                OrderStatus.ORDER_DONE));
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }
}

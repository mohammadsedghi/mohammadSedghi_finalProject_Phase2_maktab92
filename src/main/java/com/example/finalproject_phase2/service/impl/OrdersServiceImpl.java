package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomDuplicateInfoException;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithCustomerAndSubDuty;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.ordersDto.OrdersWithPriceAndBasePrice;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.repository.OrdersRepository;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import com.example.finalproject_phase2.service.impl.mapper.OrdersMapper;
import com.example.finalproject_phase2.service.impl.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final CustomerMapper customerMapper;
    private final SubDutyMapper subDutyMapper;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, CustomerMapper customerMapper, SubDutyMapper subDutyMapper) {
        this.ordersRepository = ordersRepository;
        this.customerMapper = customerMapper;
        this.subDutyMapper = subDutyMapper;
    }
    @Override
    public OrdersDto submitOrder(OrdersWithPriceAndBasePrice ordersWithPriceAndBasePrice) {
        CheckValidation checkValidation = new CheckValidation();
        CustomRegex customRegex = new CustomRegex();
        try {
            if (!customRegex.checkOneInputIsValid(ordersWithPriceAndBasePrice.getPriceOfOrders(), customRegex.getValidPrice())) {
                throw new CustomException("input ProposedPrice for orders is invalid");
            } else {
                ordersWithPriceAndBasePrice.getOrders().setProposedPrice(Double.parseDouble(ordersWithPriceAndBasePrice.getPriceOfOrders()) + ordersWithPriceAndBasePrice.getOrders().getSubDuty().getBasePrice());
            }
            if (!checkValidation.isValid(ordersWithPriceAndBasePrice.getOrders())) {
                throw new CustomException("input for orders is invalid");
            }
            if (!checkValidation.isValid(ordersWithPriceAndBasePrice.getOrders().getAddress())) {
                throw new CustomException("input address for orders is invalid");
            }
                ordersRepository.save(ordersWithPriceAndBasePrice.getOrders());
        } catch (CustomException ce) {
            return new OrdersDto();
        }
        return OrdersMapper.ordersToOrdersDto(ordersWithPriceAndBasePrice.getOrders()) ;
    }

    @Override
    public Collection<Orders> showOrdersToSpecialist(SubDutyDto subDutyDto) {
        return ordersRepository.showOrdersToSpecialist(subDutyMapper.subDutyDtoToSubDuty(subDutyDto),OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION,OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
    }

    @Override
    public Orders updateOrderToNextLevel(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus) {
        ordersDtoWithOrdersStatus.getOrders().setOrderStatus(ordersDtoWithOrdersStatus.getOrderStatus());
        ordersRepository.save(ordersDtoWithOrdersStatus.getOrders());
        return ordersDtoWithOrdersStatus.getOrders();
    }
    @Override
    public OrdersDto findOrdersWithThisCustomerAndSubDuty(OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty) {
        try {
            ordersRepository.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty.getCustomer(), ordersDtoWithCustomerAndSubDuty.getSubDuty(),OrderStatus.ORDER_DONE).ifPresent(
                orders -> {
                        throw new CustomDuplicateInfoException("this customer submit order for this subDuty");
                    });

                }catch (CustomDuplicateInfoException cdi) {
            throw new CustomDuplicateInfoException("this customer submit order for this subDuty");
        }
        return new OrdersDto();

    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(CustomerDto customerDto) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
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
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(CustomerDto customerDto) {
         return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION));
    }

    @Override
    public Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(CustomerDto customerDto) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE));
    }

    @Override
    public Collection<Orders> findOrdersInStatusStarted(CustomerDto customerDto) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
                OrderStatus.ORDER_STARTED));
    }

    @Override
    public Collection<Orders> findOrdersInStatusPaid(CustomerDto customerDto) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
                OrderStatus.ORDER_PAID));
    }

    @Override
    public Collection<Orders> findOrdersInStatusDone(CustomerDto customerDto) {
        return (ordersRepository.findOrdersInStatusWaitingForSpecialistSuggestion(customerMapper.customerDtoToCustomer(customerDto),
                OrderStatus.ORDER_DONE));
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }
}

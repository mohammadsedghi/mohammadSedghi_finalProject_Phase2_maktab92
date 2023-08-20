package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.repository.SpecialistSuggestionRepository;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerMapper;
import com.example.finalproject_phase2.service.impl.mapper.SpecialistSuggestionMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.validation.CalenderAndValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final SpecialistSuggestionRepository specialistSuggestionRepository;
    private final OrdersService ordersService;
    private final SpecialistSuggestionMapper specialistSuggestionMapper;
    private final CustomerMapper customerMapper;
    CalenderAndValidation calenderAndValidation = new CalenderAndValidation();
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public SpecialistSuggestionServiceImpl(SpecialistSuggestionRepository specialistSuggestionRepository, OrdersService ordersService, CustomerMapper customerMapper,SpecialistSuggestionMapper specialistSuggestionMapper) {
        this.specialistSuggestionRepository = specialistSuggestionRepository;
        this.ordersService = ordersService;
        this.customerMapper = customerMapper;
        this.specialistSuggestionMapper = specialistSuggestionMapper;
    }

    @Override
    public Boolean IsValidSpecialSuggestion(Specialist specialist, Orders orders, Integer workTimePerHour,
                                            int hour, int minutes, int day, int month, int year, SubDuty subDuty, Double proposedPrice) {
        try {
            if (!findSuggestWithThisSpecialistAndOrder(specialist, orders)) {
                throw new CustomException("duplicate request for suggest");
            }
            if (!calenderAndValidation.setAndConvertDate(orders.getDateOfWork(),
                    day, month, year, orders.getTimeOfWork(), hour, minutes)) {
                throw new CustomException("order time of work is not valid");
            }
            OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
            ordersDtoWithOrdersStatus.setOrders(orders);
            ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
            SpecialistSuggestion specialistSuggestion = SpecialistSuggestion.builder()
                    .specialist(specialist)
                    .order(ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus))
                    .DateOfSuggestion(LocalDate.now())
                    .TimeOfSuggestion(LocalTime.now())
                    .TimeOfStartWork(LocalTime.of(hour, minutes, 0))
                    .DateOfStartWork(LocalDate.of(year, month, day))
                    .durationOfWorkPerHour(workTimePerHour)
                    .proposedPrice(subDuty.getBasePrice() + proposedPrice)
                    .build();
            return submitSpecialistSuggestion(specialistSuggestion);
        } catch (CustomException ce) {
            throw new CustomException("submitSpecialistSuggestion is not occur");
        }
    }

    @Override
    public Boolean submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion) {
        specialistSuggestionRepository.save(specialistSuggestion);
        return true;
    }

    @Override
    public List<SpecialistSuggestion> findCustomerOrderSuggestionOnPrice(CustomerDto customerDto) {
        return specialistSuggestionRepository.findCustomerOrderSuggestionOnPrice(customerMapper.customerDtoToCustomer(customerDto));
    }
    @Override
    public List<SpecialistSuggestion> findCustomerOrderSuggestionOnScoreOfSpecialist(CustomerDto customerDto) {
        return specialistSuggestionRepository.findCustomerOrderSuggestionOnScoreOfSpecialist(customerMapper.customerDtoToCustomer(customerDto));
    }
    @Override
    public ProjectResponse changeStatusOrderToWaitingForSpecialistToWorkplace(Orders orders, Specialist specialist) {
        orders.setSpecialist(specialist);
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrders(orders);
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        return new ProjectResponse("202", "status changed");
    }

    @Override
    public SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder specialistSelectionOfOrder) {
        return specialistSelectionOfOrder;
    }

    @Override
    public SpecialistSuggestion findById(Long id) {
        return specialistSuggestionRepository.findById(id).get();
    }

    @Override
    public ProjectResponse changeStatusOrderToStarted(Orders orders, SpecialistSuggestion specialistSuggestion) {
        try {
            if (specialistSuggestion.getSpecialistSelectionOfOrder() == SpecialistSelectionOfOrder.SELECTED) {
                OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
                ordersDtoWithOrdersStatus.setOrders(orders);
                ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_STARTED);
                ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
            } else {
                throw new CustomException("customer not permission change status of this" +
                        " order to started in this level");
            }
        } catch (CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());
        }
        return new ProjectResponse("202", "status changed");
    }

    @Override
    public ProjectResponse changeStatusOrderToDone(Orders orders) {
        try {
            if (orders.getOrderStatus() == OrderStatus.ORDER_STARTED) {
                OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
                ordersDtoWithOrdersStatus.setOrders(orders);
                ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_DONE);
                ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
            } else {
                throw new CustomException("customer not permission change status of this" +
                        " order to started in this level");
            }
        } catch (CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());
        }
        return new ProjectResponse("202", "status changed");

    }


    @Override
    public boolean findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders) {
        return specialistSuggestionRepository.findSuggestWithThisSpecialistAndOrder(specialist, orders).isEmpty();
    }


}
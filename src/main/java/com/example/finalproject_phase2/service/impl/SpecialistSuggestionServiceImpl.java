package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.repository.SpecialistSuggestionRepository;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.validation.CalenderAndValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final SpecialistSuggestionRepository specialistSuggestionRepository;
    private final OrdersService ordersService;
    CalenderAndValidation calenderAndValidation = new CalenderAndValidation();
    CheckValidation checkValidation=new CheckValidation();

    @Autowired
    public SpecialistSuggestionServiceImpl(SpecialistSuggestionRepository specialistSuggestionRepository, OrdersService ordersService) {
        this.specialistSuggestionRepository = specialistSuggestionRepository;
        this.ordersService = ordersService;
    }
@Override
    public ProjectResponse IsValidSpecialSuggestion(Specialist specialist, Orders orders, Integer workTimePerHour,
        int hour,int minutes,int day,int month,int year,SubDuty subDuty, Double proposedPrice) {

        if (!findSuggestWithThisSpecialistAndOrder(specialist, orders)) {
            return new ProjectResponse("500", "duplicate request for suggest");
        }
        if (!calenderAndValidation.setAndConvertDate(orders.getDateOfWork(),
                day,month,year,orders.getTimeOfWork(),hour,minutes)) {
      return new ProjectResponse("500", "order time of work is not valid");
        }

        SpecialistSuggestion specialistSuggestion = SpecialistSuggestion.builder()
                .specialist(specialist)
                .order(ordersService.updateOrderToNextLevel(orders, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION))
                .DateOfSuggestion(LocalDate.now())
                .TimeOfSuggestion(LocalTime.now())
                .TimeOfStartWork( LocalTime.of(hour, minutes, 0))
                .DateOfStartWork(LocalDate.of(year,month,day))
                .durationOfWorkPerHour(workTimePerHour)
                .proposedPrice(subDuty.getBasePrice() + proposedPrice)
                .build();

       return submitSpecialistSuggestion(specialistSuggestion);
    }

    @Override
    public List<SpecialistSuggestion> findCustomerOrderSuggestionOnPrice(Customer customer) {
        return specialistSuggestionRepository.findCustomerOrderSuggestionOnPrice(customer);
    }

    @Override
    public List<SpecialistSuggestion> findCustomerOrderSuggestionOnScoreOfSpecialist(Customer customer) {
        return specialistSuggestionRepository.findCustomerOrderSuggestionOnScoreOfSpecialist( customer);
    }

    @Override
    public ProjectResponse changeStatusOrderToWaitingForSpecialistToWorkplace(Orders orders,Specialist specialist) {
        orders.setSpecialist(specialist);
                ordersService.updateOrderToNextLevel(orders,
                        OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);

        return new ProjectResponse("202","status changed");
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
    public ProjectResponse changeStatusOrderToStarted(Orders orders ,SpecialistSuggestion specialistSuggestion) {
       try {
           if (specialistSuggestion.getSpecialistSelectionOfOrder() == SpecialistSelectionOfOrder.SELECTED) {
               ordersService.updateOrderToNextLevel(orders,
                       OrderStatus.ORDER_STARTED);
           } else {
               throw new CustomException("customer not permission change status of this" +
                       " order to started in this level");
           }
       }catch (CustomException ce){
           return new ProjectResponse("500", ce.getMessage());
       }
        return new ProjectResponse("202","status changed");
    }

    @Override
    public ProjectResponse changeStatusOrderToDone(Orders orders) {
        try {
            if (orders.getOrderStatus() == OrderStatus.ORDER_STARTED) {
                ordersService.updateOrderToNextLevel(orders,
                        OrderStatus.ORDER_DONE);
            } else {
                throw new CustomException("customer not permission change status of this" +
                        " order to started in this level");
            }
        }catch (CustomException ce){
            return new ProjectResponse("500", ce.getMessage());
        }
        return new ProjectResponse("202","status changed");

    }

    @Override
    public ProjectResponse submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion) {
        specialistSuggestionRepository.save(specialistSuggestion);
        return new ProjectResponse("202", "specialistSuggestion is submit");
    }

    @Override
    public boolean findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders) {
        return specialistSuggestionRepository.findSuggestWithThisSpecialistAndOrder(specialist, orders).isEmpty();
    }


}
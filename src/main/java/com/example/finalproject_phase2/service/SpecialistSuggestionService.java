package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;

import java.util.List;
import java.util.Optional;

public interface SpecialistSuggestionService {
    Boolean submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion);
    boolean findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders);
    Boolean IsValidSpecialSuggestion(Specialist specialist, Orders orders, Integer workTimePerHour,
                                             int hour,int minutes,int day,int month,int year,SubDuty subDuty, Double proposedPrice);
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnPrice(CustomerDto customerDto);
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnScoreOfSpecialist(CustomerDto customerDto);
    ProjectResponse changeStatusOrderToWaitingForSpecialistToWorkplace(Orders orders,Specialist specialist);
    SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder specialistSelectionOfOrder);
    ProjectResponse changeStatusOrderToStarted(Orders orders,SpecialistSuggestion specialistSuggestion);
    ProjectResponse changeStatusOrderToDone(Orders orders);
    SpecialistSuggestion findById(Long id);



}

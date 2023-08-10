package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;

import java.util.List;
import java.util.Optional;

public interface SpecialistSuggestionService {
    ProjectResponse submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion);
    boolean findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders);
    ProjectResponse IsValidSpecialSuggestion(Specialist specialist, Orders orders, Integer workTimePerHour,
                                             int hour,int minutes,int day,int month,int year,SubDuty subDuty, Double proposedPrice);
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnPrice(Customer customer);
    List<SpecialistSuggestion> findCustomerOrderSuggestionOnScoreOfSpecialist(Customer customer);
    ProjectResponse changeStatusOrderToWaitingForSpecialistToWorkplace(Orders orders,Specialist specialist);
    SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder specialistSelectionOfOrder);
    SpecialistSuggestion findById(Long id);
    ProjectResponse changeStatusOrderToStarted(Orders orders,SpecialistSuggestion specialistSuggestion);
    ProjectResponse changeStatusOrderToDone(Orders orders);


}

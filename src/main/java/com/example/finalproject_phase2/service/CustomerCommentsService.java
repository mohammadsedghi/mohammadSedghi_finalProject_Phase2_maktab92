package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;

public interface CustomerCommentsService {
    Boolean submitCustomerCommentsService(CustomerCommentsDto customerCommentsDto);
}

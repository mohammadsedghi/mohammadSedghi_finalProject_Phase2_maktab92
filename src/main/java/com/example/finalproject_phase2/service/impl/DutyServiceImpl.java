package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    @Override
    public ProjectResponse addDuty(Duty duty) {

        try{
            if (!checkValidation.isValid(duty)) {
                throw new CustomException("this duty  is invalid");
            }
            dutyRepository.findAll().forEach(duty1 -> {
                if (duty1.getName().equals(duty.getName())) {
                    throw new CustomException("this duty name is exist");
                }
            });
            dutyRepository.save(duty);

        } catch ( CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());

        }
        return new ProjectResponse("201","duty saved");
    }
}

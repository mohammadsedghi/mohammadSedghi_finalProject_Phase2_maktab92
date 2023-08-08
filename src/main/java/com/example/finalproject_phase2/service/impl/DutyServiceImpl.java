package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.impl.mapper.DutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    @Override
    public ProjectResponse addDuty(DutyDto dutyDto) {

        try{
            if (!checkValidation.isValid(dutyDto)) {
                throw new CustomException("this duty  is invalid");
            }
            dutyRepository.findAll().forEach(duty1 -> {
                if (duty1.getName().equals(dutyDto.getName())) {
                    throw new CustomException("this duty name is exist");
                }
            });
            dutyRepository.save(DutyMapper.dutyDtoToDuty(dutyDto));

        } catch ( CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());

        }
        return new ProjectResponse("202","duty saved");
    }

    @Override
    public Set<DutyDto> findAllByDuties() {
        return DutyMapper.collectionOfDutyToSetOfDuty(dutyRepository.findAllByDuties());
    }

    @Override
    public Duty findByName(String name) {
        return dutyRepository.findByName(name);
    }
}

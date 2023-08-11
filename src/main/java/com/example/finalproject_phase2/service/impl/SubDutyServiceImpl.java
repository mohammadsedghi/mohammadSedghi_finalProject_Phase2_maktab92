package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.repository.SubDutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.impl.mapper.DutyMapper;
import com.example.finalproject_phase2.service.impl.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository subDutyRepository;
    private final DutyService dutyService;
    SubDuty subDuty;
    CheckValidation checkValidation = new CheckValidation();
    @Override
    public ProjectResponse addSubDuty(SubDutyDto subDutyDto) {
        try {
            if (!checkValidation.isValid(subDutyDto)) {
                throw new CustomException("input subDuty is invalid");
            }
            if (isExistSubDuty(subDutyDto.getName())){ throw new CustomException("duplicate subDuty is invalid");}
            SubDuty subDuty = subDutyRepository.save(SubDutyMapper.subDutyDtoToSubDuty(subDutyDto));
            Set<SubDuty> subDuties = subDutyDto.getDuty().getSubDuties();
            subDuties.add(subDuty);
            dutyService.addDuty(DutyMapper.dutyToDutyDto(subDutyDto.getDuty()));
        } catch (CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());
        }
        return new ProjectResponse("202", "sub duty is saved");
    }
    @Override
    public Set<SubDuty> showAllSubDutyOfDuty(Duty duty) {
 return new HashSet<>(subDutyRepository.showSubDutyOfDuty(duty));
    }
    @Override
    public ProjectResponse editSubDutyPrice(SubDuty subduty,String price ) {
        CustomRegex customRegex = new CustomRegex();
        try {
            if (customRegex.checkOneInputIsValid(price, customRegex.getValidPrice())) {
                subduty.setBasePrice(Double.parseDouble(price));
                    subDutyRepository.save(subduty);
            } else throw new CustomNumberFormatException("input basePrice is invalid");
        } catch (CustomNumberFormatException cnf) {
           return new ProjectResponse("500", cnf.getMessage());
        }
        return new ProjectResponse("202", "price edit");
    }
    @Override
    public ProjectResponse editSubDutyDescription(SubDuty subduty, String description) {
        CustomRegex customRegex = new CustomRegex();
        try {
            if (customRegex.checkOneInputIsValid(description, customRegex.getValidStr())) {
                subduty.setDescription(description);
                    subDutyRepository.save(subduty);
            } else {
                throw new CustomException("input description is invalid");
            }
        } catch (CustomException ce) {
            return new ProjectResponse("500", ce.getMessage());
        }
        return new ProjectResponse("202"," description edit");
    }

    @Override
    public boolean isExistSubDuty(String name) {
      AtomicBoolean flag= new AtomicBoolean(false);
            subDutyRepository.isExistSubDuty(name).ifPresent(
                    subDuty -> {
                       flag.set(true);
                    }
            );
        return flag.get();
    }

    @Override
    public SubDuty findByName(String name) {
        subDutyRepository.findByName(name).ifPresentOrElse(
              subDuty1 ->{subDuty=subDuty1;}
              ,()->{subDuty=new SubDuty();}
       );
        return subDuty;
    }
}

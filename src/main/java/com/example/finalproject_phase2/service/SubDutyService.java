package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SubDutyService {
    ProjectResponse addSubDuty(SubDutyDto subDutyDto);
    Set<SubDuty> showAllSubDutyOfDuty(Duty duty);
    ProjectResponse editSubDutyPrice(SubDuty subduty,String price);
    ProjectResponse editSubDutyDescription(SubDuty subduty,String description);
    boolean isExistSubDuty(String name);
    SubDuty findByName(String name);
}

package com.example.finalproject_phase2.service.impl.mapper;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DutyMapper {
    public static Duty dutyDtoToDuty(DutyDto dutyDto) {
        return Duty.builder()
                .name(dutyDto.getName())
                .subDuties(dutyDto.getSubDuties())
                .build();
    }

    public static Set<DutyDto> collectionOfDutyToSetOfDuty(Collection<Duty> duties) {
        DutyDto dutyDto=new DutyDto();
        Set<DutyDto> dutyDtos=new HashSet<>();
        for (Duty duty : duties
        ){
            dutyDto.setName(duty.getName());
            dutyDto.setSubDuties(duty.getSubDuties());
            dutyDtos.add(dutyDto);
        }
        return dutyDtos;
    }
}

package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SubDutyMapper {
    public static SubDuty subDutyDtoToSubDuty(SubDutyDto subDutyDto) {
        return SubDuty.builder()
                .name(subDutyDto.getName())
                .duty(subDutyDto.getDuty())
                .basePrice(subDutyDto.getBasePrice())
                .description(subDutyDto.getDescription()).
                build();
    }

    public static SubDutyDto subDutyToSubDutyDto(SubDuty subDuty) {
        return SubDutyDto.builder()
                .name(subDuty.getName())
                .duty(subDuty.getDuty())
                .basePrice(subDuty.getBasePrice())
                .description(subDuty.getDescription()).
                build();
    }

    public static Set<SubDutyDto> collectionOfSubDutyToSetOfSubDutyDto(Collection<SubDuty> SubDuties) {
        Set<SubDutyDto> subDutyDtos = new HashSet<>();
        for (SubDuty subDuty : SubDuties
        ) {
            subDutyDtos.add(SubDutyMapper.subDutyToSubDutyDto(subDuty));
        }
        return subDutyDtos;
    }

}

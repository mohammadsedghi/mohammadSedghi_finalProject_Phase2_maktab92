package com.example.finalproject_phase2.service.impl.mapper;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;

public class DutyMapper {
    public static Duty customerDtoToCustomer(DutyDto dutyDto) {
        return Duty.builder()
                .name(dutyDto.getName())
                .subDuties(dutyDto.getSubDuties())
                .build();
    }
}

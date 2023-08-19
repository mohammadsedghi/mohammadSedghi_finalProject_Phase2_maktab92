package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.entity.Specialist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class SecondSpecialistMapper {
    public static Specialist specialistDtoToSpecialist(SpecialistDto specialistDto){
        return Specialist.builder().
        duty(specialistDto.getDuty()).
        subDuties(specialistDto.getSubDuties()) .
       score(specialistDto.getScore()).
       wallet(specialistDto.getWallet()).
                status(specialistDto.getStatus()).
                firstName(specialistDto.getFirstName()).
                lastName(specialistDto.getLastName()).
                email(specialistDto.getEmail()).
                nationalId(specialistDto.getNationalId()).
                password(specialistDto.getPassword())
        .build();
    }
    public static SpecialistDto specialistToSpecialistDto(Specialist specialist){
        return SpecialistDto.builder().
                firstName(specialist.getFirstName()).
                lastName(specialist.getLastName()).
                nationalId(specialist.getNationalId()).
                password(specialist.getPassword()).
                email(specialist.getEmail()).
                status(specialist.getStatus()).
                score(specialist.getScore()).
                wallet(specialist.getWallet()).
                duty(specialist.getDuty()).
                subDuties(specialist.getSubDuties())
                .build();

    }
}

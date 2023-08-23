package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.entity.Specialist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;



@Component
public class SecondSpecialistMapper {
    public static Specialist specialistDtoToSpecialist(SpecialistDto specialistDto){
        return Specialist.builder().
        duty(specialistDto.getDuty()).
        subDuties(specialistDto.getSubDuties()) .
       score(specialistDto.getScore()).
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
                score(specialist.getScore()).
                duty(specialist.getDuty()).
                subDuties(specialist.getSubDuties())
                .build();

    }
}

package com.example.finalproject_phase2.dto.specialistDto;

import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.Wallet;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class SpecialistDto {

    Duty duty;

    Set<SubDuty> subDuties;

    Wallet wallet;
    @Enumerated(EnumType.STRING)
    SpecialistRegisterStatus status;
    @NotNull(message = "score must be have value")
    Integer score;
    @Column(name = "image_data", columnDefinition = "TEXT")
    String imageData;
}

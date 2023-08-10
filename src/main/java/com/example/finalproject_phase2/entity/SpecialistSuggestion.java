package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistSuggestion extends BaseEntity<Long> {
    @ManyToOne
    Specialist specialist;
    @ManyToOne
    Orders order;
    LocalDate DateOfSuggestion;
    LocalTime TimeOfSuggestion;
    Double proposedPrice;
    LocalTime TimeOfStartWork;
    LocalDate DateOfStartWork;
    Integer durationOfWorkPerHour;
    @Enumerated(EnumType.STRING)
    SpecialistSelectionOfOrder specialistSelectionOfOrder;

}

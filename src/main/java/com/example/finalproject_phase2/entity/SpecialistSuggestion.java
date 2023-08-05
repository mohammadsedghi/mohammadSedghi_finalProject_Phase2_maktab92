package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

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
    Integer durationOfWorkPerHour;
}

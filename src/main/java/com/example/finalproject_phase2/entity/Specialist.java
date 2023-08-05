package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Specialist extends Person{
    @ManyToOne
    Duty duty;
    @ManyToMany
    @JoinTable(
            name = "Specialist_SubDuties",
            joinColumns = @JoinColumn(name = "Specialist_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "SubDuties_ID", referencedColumnName = "id"))
    Set<SubDuty> subDuties;
    @OneToOne
    Wallet wallet;
    @Enumerated(EnumType.STRING)
    SpecialistRegisterStatus status;
    Integer score;
    String imageData;
}

package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Specialist extends Person{
    @ManyToOne
    Duty duty;
    @ManyToMany(fetch = FetchType.EAGER)
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
   @Column(name = "image_data", columnDefinition = "TEXT")
    String imageData;
@Builder
    public Specialist(String firstName, String lastName, String nationalId, String email, String password, LocalDate registerDate, LocalTime registerTime, Duty duty, Set<SubDuty> subDuties, Wallet wallet, SpecialistRegisterStatus status, Integer score) {
        super(firstName, lastName, nationalId, email, password, registerDate, registerTime);
        this.duty = duty;
        this.subDuties = subDuties;
        this.wallet = wallet;
        this.status = status;
        this.score = score;
    }
}

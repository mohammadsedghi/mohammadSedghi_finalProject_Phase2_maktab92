package com.example.finalproject_phase2.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends Person {
    @Builder
    public Admin(String firstName, String lastname, String nationalId, String email, String password, LocalDate registerDate, LocalTime registerTime) {
        super(firstName, lastname, nationalId, email, password, registerDate, registerTime);
    }
}

package com.example.finalproject_phase2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends Person {
    @OneToOne
    Wallet wallet;
@Builder
    public Customer(String firstName, String lastname, String nationalId, String email, String password, LocalDate registerDate, LocalTime registerTime, Wallet wallet) {
        super(firstName, lastname, nationalId, email, password, registerDate, registerTime);
        this.wallet = wallet;
    }
}

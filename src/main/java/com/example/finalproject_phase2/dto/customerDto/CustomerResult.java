package com.example.finalproject_phase2.dto.customerDto;

import com.example.finalproject_phase2.dto.personDto.PersonResult;
import com.example.finalproject_phase2.entity.Wallet;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResult extends PersonResult {
    Wallet wallet;

    @Builder
    public CustomerResult(String firstName, String lastname, String nationalId, String email, LocalDate registerDate, LocalTime registerTime, Wallet wallet) {
        super(firstName, lastname, nationalId, email, registerDate, registerTime);
        this.wallet = wallet;
    }
}

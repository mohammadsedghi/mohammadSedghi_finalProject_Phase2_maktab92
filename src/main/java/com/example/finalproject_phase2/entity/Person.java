package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

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
public class Person extends BaseEntity<Long> {
     String firstName;
   String lastname;
   String nationalId;
   String email;
    String password;
    LocalDate registerDate;
    LocalTime registerTime;
}

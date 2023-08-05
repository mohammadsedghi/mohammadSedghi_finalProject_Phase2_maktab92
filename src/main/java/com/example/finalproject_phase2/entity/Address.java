package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity<Long> {
    String province;
    String city;
    String street;
    String postalCode;
    Integer houseNumber;
}

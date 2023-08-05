package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import jakarta.persistence.*;
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
public class Orders extends BaseEntity<Long> {
    @ManyToOne
    Customer customer;
    @ManyToOne
    Specialist specialist;
    @ManyToOne
    SubDuty subDuty;

    Double proposedPrice;

    String description;

    LocalDate DateOfWork;
    LocalTime timeOfWork;
    @OneToOne
    Address address;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

}

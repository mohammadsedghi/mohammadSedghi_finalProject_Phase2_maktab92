package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Set;

@Setter
@Getter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
 // @Lob
    String imageData;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Specialist that = (Specialist) o;
//        return status == that.status;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(status);
//    }
}

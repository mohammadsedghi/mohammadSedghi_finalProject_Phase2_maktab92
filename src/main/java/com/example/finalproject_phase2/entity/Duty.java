package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;

import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Duty extends BaseEntity<Long> {
    String name;
    @OneToMany(mappedBy = "duty")
    Set<SubDuty> subDuties;

    @Override
    public String toString() {
        return "Duty{" +
                "name='" + name + '\'' +
                 super.toString();
    }


}

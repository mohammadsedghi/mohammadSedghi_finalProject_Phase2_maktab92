package com.example.finalproject_phase2.dto.dutyDto;

import com.example.finalproject_phase2.entity.SubDuty;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DutyDto {
    @Length(message ="name of duty must be 100 character",max = 100)
    @NotNull(message = "name must of duty be have value")
    @Pattern(message = "name must be just letters",regexp = "^[a-zA-Z]+$")
    String name;
    Set<SubDuty> subDuties;

}

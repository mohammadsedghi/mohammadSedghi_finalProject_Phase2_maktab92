package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SubDutyRepository extends JpaRepository<SubDuty,Long> {
    Collection<SubDuty> showSubDutyOfDuty(Duty duty);
    Optional<SubDuty> isExistSubDuty(String name);

}

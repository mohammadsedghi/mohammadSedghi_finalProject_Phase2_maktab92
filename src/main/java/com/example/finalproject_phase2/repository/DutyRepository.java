package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyRepository extends JpaRepository<Duty,Long> {
}

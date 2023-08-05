package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistSuggestionRepository extends JpaRepository<SpecialistSuggestion,Long> {
    Optional<SpecialistSuggestion> findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders);
}

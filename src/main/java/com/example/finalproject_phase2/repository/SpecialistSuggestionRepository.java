package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SpecialistSuggestionRepository extends JpaRepository<SpecialistSuggestion,Long> {
    @Query("select ss from SpecialistSuggestion ss where ss.specialist=:specialist and ss.order=:orders ")
    Optional<SpecialistSuggestion> findSuggestWithThisSpecialistAndOrder(Specialist specialist, Orders orders);
}

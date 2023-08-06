package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SpecialistSuggestionRepository;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final SpecialistSuggestionRepository specialistSuggestionRepository;
    @Autowired
    public SpecialistSuggestionServiceImpl(SpecialistSuggestionRepository specialistSuggestionRepository) {
        this.specialistSuggestionRepository = specialistSuggestionRepository;
    }
}

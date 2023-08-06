package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SpecialistSuggestionRepository;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;

public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final SpecialistSuggestionRepository specialistSuggestionRepository;

    public SpecialistSuggestionServiceImpl(SpecialistSuggestionRepository specialistSuggestionRepository) {
        this.specialistSuggestionRepository = specialistSuggestionRepository;
    }
}

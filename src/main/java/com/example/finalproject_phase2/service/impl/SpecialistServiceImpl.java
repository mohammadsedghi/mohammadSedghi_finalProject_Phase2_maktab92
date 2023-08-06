package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.service.SpecialistService;

public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }
}

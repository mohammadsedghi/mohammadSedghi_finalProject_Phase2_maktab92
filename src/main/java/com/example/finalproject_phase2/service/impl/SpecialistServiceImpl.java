package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }
}

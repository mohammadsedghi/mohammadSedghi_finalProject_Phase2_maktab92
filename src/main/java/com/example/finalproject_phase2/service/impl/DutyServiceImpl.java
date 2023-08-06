package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;

public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;

    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }
}

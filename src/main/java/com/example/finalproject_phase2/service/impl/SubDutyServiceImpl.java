package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SubDutyRepository;
import com.example.finalproject_phase2.service.SubDutyService;

public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository dutyRepository;

    public SubDutyServiceImpl(SubDutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }
}

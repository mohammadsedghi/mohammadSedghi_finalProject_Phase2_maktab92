package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    @Autowired
    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }
}

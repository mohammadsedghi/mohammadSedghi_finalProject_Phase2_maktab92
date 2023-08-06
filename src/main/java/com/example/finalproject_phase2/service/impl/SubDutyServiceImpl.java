package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.repository.SubDutyRepository;
import com.example.finalproject_phase2.service.SubDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository dutyRepository;
    @Autowired
    public SubDutyServiceImpl(SubDutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }
}

package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Duty;

public interface DutyService {
    ProjectResponse addDuty(Duty duty);
}

package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.impl.mapper.DutyMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/duty")
public class DutyController {
    private final DutyService dutyService;
    private final DutyMapper dutyMapper;

    public DutyController(DutyService dutyService, DutyMapper dutyMapper) {
        this.dutyService = dutyService;
        this.dutyMapper = dutyMapper;
    }

    @PostMapping("/submitDuty")
    public ResponseEntity<ProjectResponse> addDuty(@RequestBody DutyDto dutyDto) {
     return ProjectResponse.getResponseEntity(dutyService.addDuty(dutyDto));
    }
    @GetMapping("/findAll")
    public Set<DutyDto> getAllDuty() {
        return dutyService.findAllByDuties();
    }
}

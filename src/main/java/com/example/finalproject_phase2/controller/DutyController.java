package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.impl.mapper.DutyMapper;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/submit")
    public ResponseEntity<DutyDto> addDuty(@RequestBody DutyDto dutyDto) {
        DutyDto dutyDtoGenerated = dutyService.addDuty(dutyDto);
        if (dutyDtoGenerated!=null)return new ResponseEntity<>(dutyDtoGenerated, HttpStatus.ACCEPTED);
        else throw new CustomException("duty not saved");
    }
    @GetMapping("/findAll")
    public Set<DutyDto> getAllDuty() {
        return dutyService.findAllByDuties();
    }
}

package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.service.SubDutyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subDuty")
public class SubDutyController {
    private final SubDutyService subDutyService;
    public SubDutyController(SubDutyService subDutyService) {
        this.subDutyService = subDutyService;
    }

//    @PostMapping("/editSubDutyPrice")
//    public ResponseEntity<ProjectResponse> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
//        return ProjectResponse.getResponseEntity(subDutyService.editSubDutyPrice(editSubDutyDto));
//    }
}

package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.impl.mapper.SubDutyMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subDuty")
public class SubDutyController {
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;
    public SubDutyController(SubDutyService subDutyService, SubDutyMapper subDutyMapper) {
        this.subDutyService = subDutyService;
        this.subDutyMapper = subDutyMapper;
    }

//    @PostMapping("/editSubDutyPrice")
//    public ResponseEntity<ProjectResponse> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
//        return ProjectResponse.getResponseEntity(subDutyService.editSubDutyPrice(editSubDutyDto));
//    }
}

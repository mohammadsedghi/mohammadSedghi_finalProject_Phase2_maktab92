package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.impl.mapper.SubDutyMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/subDuty")
public class SubDutyController {
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;
    public SubDutyController(SubDutyService subDutyService, SubDutyMapper subDutyMapper) {
        this.subDutyService = subDutyService;
        this.subDutyMapper = subDutyMapper;
    }
    @PostMapping("/addSubDuty")
    public ResponseEntity<SubDutyDto> addSubDuty(@RequestBody SubDutyDto subDutyDto) {
        SubDutyDto subDutyDtoCandidate = subDutyService.addSubDuty(subDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDtoCandidate, HttpStatus.ACCEPTED);
        else throw  new CustomException("subDuty not saved");
    }
    @PostMapping("/editSubDutyPrice")
    public ResponseEntity<SubDutyDto> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyPrice(editSubDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @PostMapping("/editSubDutyDescription")
    public ResponseEntity<SubDutyDto> editSubDutyDescription(@RequestBody EditSubDutyDtoDescription editSubDutyDtoDescription) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyDescription(editSubDutyDtoDescription);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @GetMapping("/findAll")
    public Set<SubDutyDto> getAllDuty(DutyDto dutyDto) {
        return subDutyMapper.collectionOfSubDutyToSetOfSubDutyDto(subDutyService.showAllSubDutyOfDuty(dutyDto));
    }

}


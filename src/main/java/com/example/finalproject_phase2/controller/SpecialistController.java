package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.specialistDto.ConvertImageDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistLoginDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.impl.mapper.SecondSpecialistMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialist")
public class SpecialistController {
    private final SpecialistService specialistService;
    @Autowired
    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }
    @PostMapping("/signUp")
    public ResponseEntity<SpecialistDto> addSpecialist(@RequestBody @Valid SpecialistDto specialistDto) {
        System.out.println(specialistDto);
        SpecialistDto specialistDtoCandidate = specialistService.addSpecialist(specialistDto);
        if (specialistDtoCandidate!=null)return new ResponseEntity<>(specialistDtoCandidate, HttpStatus.ACCEPTED);
        else throw new CustomException("specialist not saved");
    }
    @PostMapping("/login")
    public ResponseEntity<SpecialistDto> loginByEmailAndPassword(@RequestBody @Valid SpecialistLoginDto specialistLoginDto) {
        SpecialistDto specialistDto = specialistService.loginByEmailAndPassword(specialistLoginDto);
        if (specialistDto!=null)return new ResponseEntity<>(specialistDto, HttpStatus.ACCEPTED);
        else throw new CustomException("specialist not found");
    }
    @PostMapping("/confirmByAdmin")
    public ResponseEntity<SpecialistDto> confirmSpecialistByAdmin(@RequestBody @Valid SpecialistDto specialistDto) {
        SpecialistDto specialistDtoCandidate = specialistService.confirmSpecialistByAdmin(specialistDto);
        if (specialistDtoCandidate!=null)return new ResponseEntity<>(specialistDtoCandidate, HttpStatus.ACCEPTED);
        else throw new CustomException("confirm by admin have error");
    }
    @PostMapping("/updateScore")
    public ResponseEntity<Integer> updateScore(@RequestBody @Valid SpecialistScoreDto specialistScoreDto) {
        Integer score  = specialistService.updateSpecialistScore(specialistScoreDto);
        if (score!=null)return new ResponseEntity<>(score, HttpStatus.ACCEPTED);
        else throw new CustomException("score has not been updated");
    }
    @PostMapping("/showImage")
    public ResponseEntity<String> showImage(@RequestBody @Valid ConvertImageDto convertImageDto) {
      try {
          specialistService.convertByteArrayToImage(convertImageDto);
          return new ResponseEntity<>("image is converted", HttpStatus.ACCEPTED);
      }catch(CustomException ce){
          throw new CustomException(ce.getMessage());
      }


    }
}

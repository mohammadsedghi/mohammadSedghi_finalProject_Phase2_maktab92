package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.impl.mapper.SpecialistMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("CustomerComments")
public class CustomerCommentsController {
    private final CustomerCommentsService customerCommentsService;
private final SpecialistMapper specialistMapper;
    @Autowired
    public CustomerCommentsController(CustomerCommentsService customerCommentsService, SpecialistMapper specialistMapper) {
        this.customerCommentsService = customerCommentsService;
        this.specialistMapper = specialistMapper;
    }

    @PostMapping("/submit")
    public ResponseEntity<Boolean> submitCustomerComments(@RequestBody @Valid CustomerCommentsDto customerCommentsDto) {
        customerCommentsService.submitCustomerCommentsService(customerCommentsDto);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

    @PostMapping("/showScore")
    public ResponseEntity<Integer> showScore(@RequestBody @Valid SpecialistDto specialistDto ) {
        Integer score = customerCommentsService.showScoreOfLastCustomerCommentsThatThisSpecialistIsExist(
                specialistMapper.specialistDtoToSpecialist(specialistDto));
        return new ResponseEntity<>(score, HttpStatus.ACCEPTED);
    }
}

package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.ValidSpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/SpecialistSuggestion")
public class SpecialistSuggestionController {
    private final SpecialistSuggestionService specialistSuggestionService;

    @Autowired
    public SpecialistSuggestionController(SpecialistSuggestionService specialistSuggestionService) {
        this.specialistSuggestionService = specialistSuggestionService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Boolean> IsValidSpecialSuggestion(@RequestBody @Valid ValidSpecialistSuggestionDto validSpecialistSuggestionDto) {
        specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnScore")
    public ResponseEntity<List<SpecialistSuggestionDto>> findCustomerOrderSuggestionOnScoreOfSpecialist(@RequestBody @Valid CustomerDto customerDto) {
        List<SpecialistSuggestionDto> customerOrderSuggestionOnScoreOfSpecialist = specialistSuggestionService.findCustomerOrderSuggestionOnScoreOfSpecialist(customerDto);
        return new ResponseEntity<>(customerOrderSuggestionOnScoreOfSpecialist, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findCustomerOrderSuggestionOnPrice")
    public ResponseEntity<List<SpecialistSuggestionDto>> findCustomerOrderSuggestionOnPrice(@RequestBody @Valid CustomerDto CustomerDto ) {
        List<SpecialistSuggestionDto> customerOrderSuggestionOnPrice = specialistSuggestionService.findCustomerOrderSuggestionOnPrice(CustomerDto);
        return new ResponseEntity<>(customerOrderSuggestionOnPrice, HttpStatus.ACCEPTED);
    }
    @PostMapping("/findSuggestWithThisSpecialistAndOrder")
    public ResponseEntity<Boolean> findSuggestWithThisSpecialistAndOrder(@RequestBody @Valid StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist ) {
        specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeSpecialistSelectedOfOrder")
    public ResponseEntity<SpecialistSelectionOfOrder> changeSpecialistSelectedOfOrder(@RequestBody @Valid SpecialistSelectionOfOrder specialistSelectionOfOrder  ) {
        SpecialistSelectionOfOrder specialistSelectionOfOrderCandidate = specialistSuggestionService.changeSpecialistSelectedOfOrder(specialistSelectionOfOrder);
        return new ResponseEntity<>(specialistSelectionOfOrderCandidate, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeStatusOrderToStarted")
    public ResponseEntity<Boolean> changeStatusOrderToStarted(@RequestBody @Valid StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto  ) {
      specialistSuggestionService.changeStatusOrderToStarted(statusOrderSpecialistSuggestionDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeStatusOrderToDone")
    public ResponseEntity<Boolean> changeStatusOrderToDone(@RequestBody @Valid OrdersDto ordersDto) {
       specialistSuggestionService.changeStatusOrderToDone(ordersDto);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
    @PostMapping("/changeStatusOrderToWaitingForSpecialistToWorkplace")
    public ResponseEntity<Boolean> changeStatusOrderToWaitingForSpecialistToWorkplace(@RequestBody @Valid  StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist  statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist ) {
        specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}

package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerSignUpDto;
import com.example.finalproject_phase2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @PostMapping("/deleteAddress")
    public ResponseEntity<ProjectResponse> deleteAddress(@RequestBody AddressDto addressDto) {

//        return ProjectResponse.getResponseEntity(addressService.removeAddress(addressDto));
        return null;
    }
}

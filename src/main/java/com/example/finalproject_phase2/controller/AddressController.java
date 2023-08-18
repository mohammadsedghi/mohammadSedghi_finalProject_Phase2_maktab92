package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.impl.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
     private final AddressMapper addressMapper;

    @Autowired
    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }
    @PostMapping("/deleteAddress")
    public ResponseEntity<AddressDto> deleteAddress(@RequestBody  AddressDto addressDto) {

            AddressDto address = addressService.removeAddress(addressDto);

      if (address!=null) return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
      else
            throw new CustomException("address not found");
    }
    @PostMapping("/submitAddress")
    public ResponseEntity<AddressDto> submitAddress(@RequestBody  AddressDto addressDto) {
        Address address = addressService.createAddress(addressDto);
        if (address!=null) return new ResponseEntity<>(addressMapper.addressToAddressDto(address), HttpStatus.ACCEPTED);
        else
            throw  new CustomException("address not saved");
    }

}


//       return ProjectResponse.getResponseEntity(addressService.removeAddress(addressDto));
//        return null;
package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.GlobalException;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.impl.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.apache.coyote.http11.Constants.a;

@RestController
@RequestMapping("/address")
@ControllerAdvice
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping("/deleteAddress")
    public ResponseEntity<AddressDto> deleteAddress(@RequestBody AddressDto addressDto) {

            AddressDto address = AddressMapper.addressToAddressDto(addressService.
                    removeAddress(AddressMapper.addressDtoToAddress(addressDto)));

      if (address!=null) return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
      else
            throw new CustomException("address not found");
    }
//    @ExceptionHandler(CustomException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleAddressNotFoundException(CustomException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
    @PostMapping("/submitAddress")
    public ResponseEntity<AddressDto> submitAddress(@RequestBody @Validated AddressDto addressDto) {
        throw  new CustomException("address not saved");
//       Address address = addressService.createAddress(addressDto);
//
//        if (address!=null) return new ResponseEntity<>(AddressMapper.addressToAddressDto(address), HttpStatus.ACCEPTED);
//        else
//            throw  new CustomException("address not saved");
    }
//    @ExceptionHandler(CustomException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleAddress(CustomException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
}


//       return ProjectResponse.getResponseEntity(addressService.removeAddress(addressDto));
//        return null;
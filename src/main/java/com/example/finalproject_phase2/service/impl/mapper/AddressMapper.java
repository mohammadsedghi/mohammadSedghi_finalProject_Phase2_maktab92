package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;

public class AddressMapper {
    public static Address addressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .province(addressDto.getProvince())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .postalCode(addressDto.getPostalCode())
                .houseNumber(addressDto.getHouseNumber())
                .build();
    }
}

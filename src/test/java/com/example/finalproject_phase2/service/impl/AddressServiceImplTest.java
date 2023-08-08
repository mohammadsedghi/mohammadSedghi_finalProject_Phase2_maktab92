package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.service.impl.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceImplTest {
    @Autowired
    AddressService addressService;
    AddressDto addressDto;
    MotherObject motherObject;

    @BeforeEach
    void setUp() {
        addressDto=new AddressDto();
        motherObject=new MotherObject();
    }

    @Test
    void deleteAddress() {
        assertEquals(motherObject.getProjectResponseOfAddressRemoved(),addressService.removeAddress(motherObject.getValidAddressDto()));
    }
    @Test
    void createAddress() {
        assertEquals(AddressMapper.addressDtoToAddress(motherObject.getValidAddressDto()),addressService.createAddress(motherObject.getValidAddressDto()));
    }
}
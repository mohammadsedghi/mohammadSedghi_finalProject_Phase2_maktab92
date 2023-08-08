package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.service.DutyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DutyServiceImplTest {
    @Autowired
    DutyService dutyService;
    MotherObject motherObject;
    DutyDto dutyDto;
    @BeforeEach
    void setUp() {
      motherObject=new MotherObject();
      dutyDto=new DutyDto();
    }

    @Test
    void addDuty() {
        assertEquals("202",dutyService.addDuty(motherObject.getValidDutyDto()));
    }

    @Test
    void findAllByDuties() {
      assertEquals(motherObject.setOfDuty(),dutyService.findAllByDuties());
    }
    @Test
    void findDutyByName() {
        assertEquals("AAA",dutyService.findByName("AAA").getName());
    }
}
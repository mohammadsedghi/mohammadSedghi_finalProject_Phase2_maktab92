package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.impl.mapper.DutyMapper;
import com.example.finalproject_phase2.service.impl.mapper.SubDutyMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubDutyServiceImplTest {
    @Autowired
    SubDutyService subDutyService;
  @Autowired
    DutyService dutyService;
    MotherObject motherObject;
    SubDutyDto subDutyDto;
    EditSubDutyDto editSubDutyDto;
    static SubDutyDto validSubDutyDto;
    @BeforeEach
    void setUp() {
        motherObject =new MotherObject();
        subDutyDto =new SubDutyDto();
        editSubDutyDto=new EditSubDutyDto();
    }


    @Test
    void addSubDuty() {
        validSubDutyDto = motherObject.getValidSubDutyDto();
        assertEquals("500",subDutyService.addSubDuty(validSubDutyDto).getCode());
    }

    @Test
    void showAllSubDutyOfDuty() {
     Set<SubDuty> subDuties=new HashSet<>();
     subDuties.add(subDutyService.findByName("CD"));
        List<SubDuty> list1=new ArrayList<>(subDuties);
        List<SubDuty> list2=new ArrayList<>(subDutyService.showAllSubDutyOfDuty( dutyService.findByName("AAA")));

// assertEquals(subDuties,subDutyService.showAllSubDutyOfDuty(DutyMapper.dutyToDutyDto(dutyService.findByName("AAA"))));
      // assertEquals(subDuties,subDutyService.showAllSubDutyOfDuty( dutyService.findByName("AAA")));
        assertEquals(list1.get(0).getName(),list2.get(0).getName());
    }

    @Test
    void editSubDutyPrice() {
      assertEquals("202",subDutyService.
              editSubDutyPrice(subDutyService.findByName("AB"),"500").getCode());
    }
//    @Test
//    void findByName() {
//       assertEquals("202",subDutyService.findByName("AB"));
//    }

    @Test
    void editSubDutyDescription() {
        assertEquals("202",subDutyService.
                editSubDutyDescription(subDutyService.findByName("AB"),"YYY").getCode());
    }

    @Test
    void isExistSubDutyTrueResult() {
        assertTrue(subDutyService.isExistSubDuty("AB"));
    }
    @Test
    void isExistSubDutyFalseResult() {
        assertFalse(subDutyService.isExistSubDuty("ppp"));
    }
}
package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.dto.specialistDto.*;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.Wallet;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.WalletService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SpecialistMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpecialistServiceImplTest {
    @Autowired
    SpecialistService specialistService;
    @Autowired
    DutyService dutyService;
    @Autowired
    DutyMapper dutyMapper;
    @Autowired
    SubDutyMapper subDutyMapper;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    WalletService walletService;
    @Autowired
    SpecialistMapper specialistMapper;
    Duty duty;
    SubDuty subDuty;
    MotherObject motherObject;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
//        Duty duty=new Duty();
//        duty.setId(1l);
//        duty.setName("AAA");
//        SubDuty subDuty=new SubDuty(duty,"AB",500.0,"YYY");
//        subDuty.setId(602l);
//        subDuty.setName("AB");
//        subDuty.BasePrice(500.0);
//        subDuty.setDescription(602l);

    }

    @Test
    @Order(1)

    void addSpecialist() throws CustomInputOutputException {
        Duty duty1 = dutyMapper.dutyDtoToDuty(dutyService.findByName("AAA"));
        SubDuty subDuty1 =subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD")) ;
      // SubDuty subDuty2 = subDutyService.findByName("AB");
        Set<SubDuty> subDutySet = new HashSet<>();
        subDutySet.add(subDuty1);
       // subDutySet.add(subDuty2);
        Wallet wallet = walletService.createWallet();
//        String image = specialistService.convertImageToImageData("src/main/java/ir/maktab/util/images/2.jpg");
        String image = specialistService.convertImageToImageData("src/main/java/com/example/finalproject_phase2/util/images/300.jpg");
        Specialist specialist = new Specialist(duty1, subDutySet, wallet,
                SpecialistRegisterStatus.WAITING_FOR_CONFIRM, 0, image);
       // specialist.setFirstName("ali");
        specialist.setFirstName("mohammad");
        specialist.setLastName("se");
        specialist.setNationalId("4560116814");
        //specialist.setEmail("ali@gmail.com");
        specialist.setEmail("mohammad@gmail.com");
        specialist.setPassword("123456al");
        specialist.setRegisterDate(LocalDate.now());
        specialist.setRegisterTime(LocalTime.now());
        specialistService.register(specialistMapper.specialistToSpecialistDto(specialist));
        ConvertImageDto imageDto=new ConvertImageDto();
        imageDto.setSpecialist(specialist);
        imageDto.setFilePath("t.jpg");
        specialistService.convertByteArrayToImage(imageDto);
    }

    @Test
    @Transactional
    @Order(4)
    void loginByEmailAndPassword() {
        SpecialistLoginDto specialistLoginDto=new SpecialistLoginDto();
        specialistLoginDto.setEmail("ali@gmail.com");
        specialistLoginDto.setPassword("123456al");
        assertEquals(specialistLoginDto, specialistService.loginByEmailAndPassword(specialistLoginDto ));
    }

    @Test
    @Order(3)
    void confirmSpecialistByAdmin() {

        specialistService.confirmSpecialistByAdmin(specialistMapper.specialistToSpecialistDto(specialistService.findByEmail("ali@gmail.com")));
    }

    @Test

    void addSpecialistToSubDuty() {
        SpecialistSubDutyDto specialistSubDutyDto=new SpecialistSubDutyDto();
        specialistSubDutyDto.setSpecialist(specialistService.findByEmail("ali@gmail.com"));
        specialistSubDutyDto.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("AB")) );
   specialistService.addSpecialistToSubDuty(specialistSubDutyDto);
    }

    @Test
    void changePassword() {
        SpecialistChangePasswordDto specialistChangePasswordDto=new SpecialistChangePasswordDto();
        specialistChangePasswordDto.setEmail("ali@gmail.com");
        specialistChangePasswordDto.setOldPassword("123456al");
        specialistChangePasswordDto.setNewPassword("123456al");
       assertEquals(true, specialistService.changePassword(specialistChangePasswordDto));
    }

    @Test
    void removeSpecialistFromDuty() {
        specialistService.removeSpecialistFromDuty();
    }

    @Test
    @Order(2)
    void encryptSpecialistPassword() {
        String hashPassword = "1r4XLSqOFqcSothyoCIusZQ1xrReUHyK5jiq2N01BgVev4RCaCenVLKZDHm1QddXcR2BKgZE5/EU/Mwl7LNXhQ==";
        assertEquals(hashPassword, specialistService.encryptSpecialistPassword("123456al"));

    }
    @Test
    void updateSpecialistScore(){
        Specialist specialist = specialistService.findByEmail("ali@gmail.com");
        SpecialistScoreDto specialistScoreDto=new SpecialistScoreDto();
        specialistScoreDto.setScore(2);
        specialistScoreDto.setSpecialist(specialist);
        assertEquals(specialistScoreDto.getScore(),specialistService.updateSpecialistScore(specialistScoreDto));
    }

}
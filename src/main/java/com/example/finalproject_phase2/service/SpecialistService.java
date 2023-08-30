package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.specialistDto.*;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface SpecialistService {
     AuthenticationResponse register(SpecialistDto specialistDto);
    AuthenticationResponse authenticate(SpecialistLoginDto specialistLoginDto);
    SpecialistDto loginByEmailAndPassword(SpecialistLoginDto specialistLoginDto);
    SpecialistDto confirmSpecialistByAdmin(SpecialistDto specialistDto);
    Boolean addSpecialistToSubDuty(SpecialistSubDutyDto specialistSubDutyDto);
    Boolean changePassword(SpecialistChangePasswordDto specialistChangePasswordDto);
    void removeSpecialistFromDuty();
    String encryptSpecialistPassword(String password);
     String convertImageToImageData(String imagePath) throws CustomInputOutputException;
     void convertByteArrayToImage (ConvertImageDto convertImageDto );
     Specialist findByEmail(String email);
    Integer updateSpecialistScore(SpecialistScoreDto specialistScoreDto);
     List<Specialist> searchSpecialist(SpecialistDto specialistDto);
}

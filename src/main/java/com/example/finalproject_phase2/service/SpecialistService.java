package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;

import java.util.Optional;

public interface SpecialistService {
    ProjectResponse addSpecialist(Specialist specialist);
    ProjectResponse loginByEmailAndPassword(String email, String password);
    ProjectResponse confirmSpecialistByAdmin(Specialist specialist);
    Boolean addSpecialistToSubDuty(Specialist specialist, SubDuty subDuty);
    ProjectResponse changePassword(String email,String oldPassword,String newPassword);
    void removeSpecialistFromDuty();
    String encryptSpecialistPassword(String password);
     String convertImageToImageData(String imagePath) throws CustomInputOutputException;
     void convertByteArrayToImage (Specialist specialist, String newFilePath );
     Specialist findByEmail(String email);
    ProjectResponse updateSpecialistScore(Integer score,Specialist specialist);
}

package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public ProjectResponse addSpecialist(Specialist specialist) {
        try {
            if (!checkValidation.isValid(specialist))throw new CustomException("input  is invalid  ");
            specialistRepository.findByEmail(specialist.getEmail()).ifPresentOrElse(
                    tempCustomer -> {throw new CustomException("Specialist with this email and password is exist ");
                    }, () -> {
              specialist.setPassword(encryptSpecialistPassword(specialist.getPassword()));
           specialistRepository.save(specialist);
                    });
        }catch (CustomException c){
            return new ProjectResponse("500", c.getMessage());
        }
        return new ProjectResponse("202", "specialist saved(created)");
    }

    @Override
    public ProjectResponse loginByEmailAndPassword(String email, String password) {
        try {
            if (checkValidation.isValidEmail(email) && checkValidation.isValidPassword(password)) {
                specialistRepository.findByEmailAndPassword(email, encryptSpecialistPassword(password)).ifPresentOrElse(
                        specialist -> {
                            CheckValidation.memberTypespecialist = specialist;
                        }
                        , () -> {
                            throw new CustomNoResultException("specialist not found");
                        }
                );
            } else {
                throw new CustomNoResultException("you inter invalid input for login");
            }
        } catch (CustomNoResultException c) {
            CheckValidation.memberTypeCustomer = new Customer();
            return new ProjectResponse("500", c.getMessage());
        }
        return new ProjectResponse("202", "specialist accepted");
    }

    @Override
    public ProjectResponse confirmSpecialistByAdmin(Specialist specialist) {
        try {
            Set<Specialist> unConfirmSpecialist = new HashSet<>(specialistRepository.showUnConfirmSpecialist(SpecialistRegisterStatus.WAITING_FOR_CONFIRM));
            if (unConfirmSpecialist.size() == 0) {
                throw new CustomNoResultException("no specialist unConfirm found");
            } else {
                for (Specialist specialistCandidate : unConfirmSpecialist
                ) {
                    if (specialistCandidate.equals(specialist)) {
                        specialist.setStatus(SpecialistRegisterStatus.CONFIRM);
                        specialistRepository.save(specialist);
                    }
                }
            }
        }catch (CustomNoResultException cnr){
            return new ProjectResponse("500", cnr.getMessage());
        }
        return new ProjectResponse("202", "specialist accepted");
    }

    @Override
    public Boolean addSpecialistToSubDuty(Specialist specialist, SubDuty a) {

        Set<SubDuty> setOfSubDuty=specialist.getSubDuties();
        specialist.getSubDuties().forEach(element -> {
            if (! specialist.getSubDuties().contains(a)) {
                specialist.getSubDuties().add(a);
                specialistRepository.save(specialist);
            }else {
                throw new CustomException("this specialist added to this subDuty before");
            }
        });
      //  System.out.println("bbbbbb"+setOfSubDuty);
//        for (SubDuty subDutyCandidate:setOfSubDuty
//        ) {
//            if (subDutyCandidate!=a){
//                setOfSubDuty.add(a);
//                System.out.println("1111111111"+setOfSubDuty);
//                System.out.println("aaaaaaaa"+a);
//                specialist.setSubDuties(setOfSubDuty);
//               specialistRepository.save(specialist);
//                return true;
//            }else {
//                throw new CustomException("this specialist added to this subDuty before");
//            }
//        }
        return false;
    }

    @Override
    public ProjectResponse changePassword(String email, String oldPassword, String newPassword) {
        try {
            if (checkValidation.isValidEmail(email) && checkValidation.isValidPassword(oldPassword)) {
                if (checkValidation.isValidPassword(newPassword)) {
                    specialistRepository.findByEmailAndPassword(email, encryptSpecialistPassword(oldPassword)).ifPresentOrElse(
                            specialist -> {
                                specialist.setPassword(encryptSpecialistPassword(newPassword));
                                specialistRepository.save(specialist);
                            }, () -> {
                                throw new CustomNoResultException("this customer is not found");
                            }
                    );
                } else {
                    throw new CustomNoResultException("new password is invalid");
                }
            } else {
                throw new CustomNoResultException("email and old password is invalid");
            }
        }catch (CustomNoResultException c){
            return new ProjectResponse("500", c.getMessage());
        }
        return new ProjectResponse("202", "password changed");
    }

    @Override
    public void removeSpecialistFromDuty() {
        Set<Specialist> confirmSpecialist=new HashSet<>(specialistRepository.showConfirmSpecialist(SpecialistRegisterStatus.CONFIRM));
        if(confirmSpecialist.size()==0){
            System.out.println("no specialist unConfirm found");
        }else{
            for (Specialist specialist:confirmSpecialist
            ) {
                    specialist.setStatus(SpecialistRegisterStatus.WAITING_FOR_CONFIRM);
                        specialistRepository.save(specialist);
                }
            }
        }


    @Override
    public String encryptSpecialistPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }

    public String convertImageToImageData(String imagePath) throws CustomInputOutputException {
        try {
            if (!checkValidation.isJpgImage(imagePath))
                throw new CustomInputOutputException("image file format is not valid with prefix without jpg");

            byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
            if (!checkValidation.isJpgImage(fileContent))
                throw new CustomInputOutputException("image file format is not valid");
            if (!checkValidation.isImageHaveValidSize(fileContent)) {
                throw new CustomInputOutputException("image size must be lower than 300KB");
            }
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException | CustomInputOutputException e) {
            throw new CustomInputOutputException(e.getMessage());
        }
    }

        public void convertByteArrayToImage (Specialist specialist, String newFilePath ){

            Optional<Specialist> SpecialistId = specialistRepository.findById(specialist.getId());
            SpecialistId.ifPresentOrElse(member -> {
                byte[] imageData = Base64.getDecoder().decode(member.getImageData());
                try (FileOutputStream fileOutputStream = new FileOutputStream(newFilePath)) {
                    fileOutputStream.write(imageData);
                } catch (IOException e) {

                }
            }, () -> {
                try {
                    throw new CustomInputOutputException("no specialist found");
                } catch (CustomInputOutputException e) {

                }
            });

        }

    @Override
    public Specialist findByEmail(String email) {
        return specialistRepository.findByEmail(email).get();
    }
}
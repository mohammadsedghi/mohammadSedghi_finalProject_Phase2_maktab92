package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.specialistDto.*;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.impl.mapper.SecondSpecialistMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
//    private final SpecialistMapper specialistMapper;
    CheckValidation checkValidation=new CheckValidation();
    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
//        this.specialistMapper = specialistMapper;
    }

    @Override
    public SpecialistDto addSpecialist(SpecialistDto specialistDto) {
        try {
            if (!checkValidation.isValid(specialistDto))throw new CustomException("input  is invalid  ");
            specialistRepository.findByEmail(specialistDto.getEmail()).ifPresentOrElse(
                    tempCustomer -> {throw new CustomException("Specialist with this email and password is exist ");
                    }, () -> {
                        specialistDto.setPassword(encryptSpecialistPassword(specialistDto.getPassword()));
                        Specialist specialist = SecondSpecialistMapper.specialistDtoToSpecialist(specialistDto);
                        specialist.setRegisterDate(LocalDate.now());
                        specialist.setRegisterTime(LocalTime.now());
                        specialistRepository.save(specialist);
                    });
        }catch (CustomException c){
            return new SpecialistDto();
        }
        return specialistDto;
    }

    @Override
    public SpecialistDto loginByEmailAndPassword(SpecialistLoginDto specialistLoginDto) {
        try {
            if (checkValidation.isValidEmail(specialistLoginDto.getEmail()) && checkValidation.isValidPassword(specialistLoginDto.getPassword())) {
                specialistRepository.findByEmailAndPassword(specialistLoginDto.getEmail(), encryptSpecialistPassword(specialistLoginDto.getPassword())).ifPresentOrElse(
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
            return new SpecialistDto();
        }
       return  SecondSpecialistMapper.specialistToSpecialistDto(CheckValidation.memberTypespecialist);
    }

    @Override
    public SpecialistDto confirmSpecialistByAdmin(SpecialistDto specialistDto) {
        try {
            Set<Specialist> unConfirmSpecialist = new HashSet<>(specialistRepository.showUnConfirmSpecialist(SpecialistRegisterStatus.WAITING_FOR_CONFIRM));
            if (unConfirmSpecialist.size() == 0) {
                throw new CustomNoResultException("no specialist unConfirm found");
            } else {
                for (Specialist specialistCandidate : unConfirmSpecialist
                ) {
                    if (specialistCandidate.equals(SecondSpecialistMapper.specialistDtoToSpecialist(specialistDto))) {
                        SecondSpecialistMapper.specialistDtoToSpecialist(specialistDto).setStatus(SpecialistRegisterStatus.CONFIRM);
                        specialistRepository.save(SecondSpecialistMapper.specialistDtoToSpecialist(specialistDto));
                    }
                }
            }
        }catch (CustomNoResultException cnr){
            return new SpecialistDto();
        }
        return specialistDto;
    }

    @Override
    public Boolean addSpecialistToSubDuty(SpecialistSubDutyDto specialistSubDutyDto) {

        Set<SubDuty> setOfSubDuty=specialistSubDutyDto.getSpecialist().getSubDuties();
        specialistSubDutyDto.getSpecialist().getSubDuties().forEach(element -> {
            if (! specialistSubDutyDto.getSpecialist().getSubDuties().contains(specialistSubDutyDto.getSubDuty())) {
                specialistSubDutyDto.getSpecialist().getSubDuties().add(specialistSubDutyDto.getSubDuty());
                specialistRepository.save(specialistSubDutyDto.getSpecialist());
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
    public Boolean changePassword(SpecialistChangePasswordDto specialistChangePasswordDto) {
        try {
            if (checkValidation.isValidEmail(specialistChangePasswordDto.getEmail()) && checkValidation.isValidPassword(specialistChangePasswordDto.getOldPassword())) {
                if (checkValidation.isValidPassword(specialistChangePasswordDto.getNewPassword())) {
                    specialistRepository.findByEmailAndPassword(specialistChangePasswordDto.getEmail(), encryptSpecialistPassword(specialistChangePasswordDto.getOldPassword())).ifPresentOrElse(
                            specialist -> {
                                specialist.setPassword(encryptSpecialistPassword(specialistChangePasswordDto.getNewPassword()));
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
            return false;
        }
        return true;
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

        public void convertByteArrayToImage (ConvertImageDto convertImageDto){

            Optional<Specialist> SpecialistId = specialistRepository.findById(convertImageDto.getSpecialist().getId());
            SpecialistId.ifPresentOrElse(member -> {
                byte[] imageData = Base64.getDecoder().decode(member.getImageData());
                try (FileOutputStream fileOutputStream = new FileOutputStream(convertImageDto.getFilePath())) {
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

    @Override
    public Integer updateSpecialistScore(SpecialistScoreDto specialistScoreDto) {
        specialistScoreDto.getSpecialist().setScore(specialistScoreDto.getScore());
        specialistRepository.save(specialistScoreDto.getSpecialist());
        return  specialistScoreDto.getScore();
    }
}
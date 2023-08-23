package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.impl.mapper.CustomerCommentsMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommentsServiceImpl implements CustomerCommentsService {
    private final CustomerCommentsRepository customerCommentsRepository;
    private final SpecialistService specialistService;
    @Autowired
    public CustomerCommentsServiceImpl(CustomerCommentsRepository customerCommentsRepository, SpecialistService specialistService) {
        this.customerCommentsRepository = customerCommentsRepository;
        this.specialistService = specialistService;
    }
CheckValidation checkValidation=new CheckValidation();
    @Override
    public Boolean submitCustomerCommentsService(CustomerCommentsDto customerCommentsDto) {
        try {
          if (!checkValidation.isValid(customerCommentsDto)) throw new CustomException("invalid customerComments");
        }catch (CustomException ce){
            throw new CustomException("invalid customerComments");
        }
        customerCommentsRepository.save(CustomerCommentsMapper.customerCommentsDtoToCustomerComments(customerCommentsDto));
        SpecialistScoreDto specialistScoreDto=new SpecialistScoreDto();
        specialistScoreDto.setSpecialist(CustomerCommentsMapper.customerCommentsDtoToCustomerComments(customerCommentsDto).getOrders().getSpecialist());
        specialistScoreDto.setScore(CustomerCommentsMapper.customerCommentsDtoToCustomerComments(customerCommentsDto).getScore());
        specialistService.updateSpecialistScore(specialistScoreDto);
  return true;
    }
}

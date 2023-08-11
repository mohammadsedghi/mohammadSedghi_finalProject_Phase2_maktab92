package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.SpecialistService;
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
    public ProjectResponse submitCustomerCommentsService(CustomerComments customerComments) {
        try {
          if (!checkValidation.isValid(customerComments)) throw new CustomException("invalid customerComments");
        }catch (CustomException ce){
            return new ProjectResponse("500", ce.getMessage());
        }
        customerCommentsRepository.save(customerComments);
        specialistService.updateSpecialistScore(customerComments.getScore(),
                customerComments.getOrders().getSpecialist());
  return new ProjectResponse("202","customerComments accepted");
    }
}

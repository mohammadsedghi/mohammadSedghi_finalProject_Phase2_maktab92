package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommentsServiceImpl implements CustomerCommentsService {
    private final CustomerCommentsRepository customerCommentsRepository;
    @Autowired
    public CustomerCommentsServiceImpl(CustomerCommentsRepository customerCommentsRepository) {
        this.customerCommentsRepository = customerCommentsRepository;
    }
CheckValidation checkValidation=new CheckValidation();
    @Override
    public ProjectResponse submitCustomerCommentsService(CustomerComments customerComments) {
        try {
          if (!checkValidation.isValid(customerComments)) throw new CustomException("invalid customerComments");
        }catch (CustomException ce){
            return new ProjectResponse("500", ce.getMessage());
        }
  return new ProjectResponse("500","customerComments accepted");
    }
}

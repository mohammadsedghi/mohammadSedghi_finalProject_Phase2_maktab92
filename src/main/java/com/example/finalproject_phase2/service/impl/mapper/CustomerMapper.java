package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerEmailDto;
import com.example.finalproject_phase2.entity.Customer;
import org.mapstruct.Mapper;

@Mapper()
public interface CustomerMapper {
      Customer customerDtoToCustomer(CustomerDto customerDto);
      CustomerDto customerToCustomerDto(Customer customer);
      CustomerEmailDto customerEmailDtoToCustomerEmail(CustomerEmailDto customerEmailDto);
      Customer customerChangePasswordDtoToCustomer(CustomerChangePasswordDto customerChangePasswordDto);

}

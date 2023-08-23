package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommentsMapper {
    public static CustomerCommentsDto customerCommentsToCustomerCommentsDto(CustomerComments customerComments) {
        return CustomerCommentsDto.builder()
                .description(customerComments.getDescription())
                .orders(customerComments.getOrders())
                .score(customerComments.getScore())
                .build();
    }

    public static CustomerComments customerCommentsDtoToCustomerComments(CustomerCommentsDto customerCommentsDto) {
        return CustomerComments.builder()
                .description(customerCommentsDto.getDescription())
                .orders(customerCommentsDto.getOrders())
                .score(customerCommentsDto.getScore())
                .build();
    }
}

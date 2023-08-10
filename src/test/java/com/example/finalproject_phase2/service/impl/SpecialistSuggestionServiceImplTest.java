package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpecialistSuggestionServiceImplTest {
    @Autowired
    SpecialistSuggestionService specialistSuggestionService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
     CustomerService customerService;
    @BeforeEach
    void setUp() {

    }

    @Test
    void isValidSpecialSuggestion() {
      assertEquals("202" ,specialistSuggestionService.IsValidSpecialSuggestion(specialistService.findByEmail("mohammad@gmail.com"),
               ordersService.findById(1l).get(), 12,2,50,
              12, 9,2023,ordersService.findById(1l).get().getSubDuty(),140d)
              .getCode());
    }
    @Test
    void inValidSpecialSuggestion() {
        assertEquals("500" ,specialistSuggestionService.IsValidSpecialSuggestion(specialistService.findByEmail("ali@gmail.com"),
                        ordersService.findById(1l).get(), 12,2,50,
                        4, 8,2023,ordersService.findById(1l).get().getSubDuty(),120d)
                .getCode());
    }
    @Test
    void findCustomerOrderSuggestionOnPrice(){

         specialistSuggestionService.findCustomerOrderSuggestionOnPrice
                 (customerService.findByEmail("mahan@gmail.com").get()).
                 forEach(specialistSuggestion -> System.out.println(specialistSuggestion.getProposedPrice()));
    }
    @Test
    void findCustomerOrderSuggestionOnScoreOfSpecialist(){

        specialistSuggestionService.findCustomerOrderSuggestionOnPrice
                        (customerService.findByEmail("mahan@gmail.com").get()).
                forEach(specialistSuggestion -> System.out.println(specialistSuggestion.getSpecialist().getScore()));
    }
    @Test
    void findSuggestWithThisSpecialistAndOrder() {
        assertTrue(specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(specialistService.findByEmail("ali@gmail.com"),
                ordersService.findById(1l).get()));

    }
    @Test
   void changeStatusOrderToWaitingForSpecialistToWorkplace(){
      assertEquals("202",specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(ordersService.findById(1l).get(),
              specialistService.findByEmail("ali@gmail.com")));
   }
   @Test
    void changeSpecialistSelectedOfOrder(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       specialistSuggestion.setSpecialistSelectionOfOrder( specialistSuggestionService.changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder.SELECTED));
        specialistSuggestionService.submitSpecialistSuggestion(specialistSuggestion);
   }
   @Test
    void changeStatusOrderToStarted(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
        assertEquals("202",specialistSuggestionService.changeStatusOrderToStarted(ordersService.findById(1l).get(),specialistSuggestion).getCode());
   }
   @Test
    void changeStatusOrderToDone(){
       assertEquals("202",specialistSuggestionService.changeStatusOrderToDone(ordersService.findById(1l).get()).getCode());
   }
}
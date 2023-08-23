package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
@Component
public class SpecialistSuggestionMapper {
    public static SpecialistSuggestionDto specialistSuggestionToSpecialistsuggestionDto(SpecialistSuggestion specialistSuggestion){
        return  SpecialistSuggestionDto.builder()
                .specialist(specialistSuggestion.getSpecialist())
                .dateOfSuggestion(specialistSuggestion.getDateOfSuggestion())
                .dateOfStartWork(specialistSuggestion.getDateOfStartWork())
                .timeOfSuggestion(specialistSuggestion.getTimeOfSuggestion())
                .timeOfStartWork(specialistSuggestion.getTimeOfStartWork())
                .durationOfWorkPerHour(specialistSuggestion.getDurationOfWorkPerHour())
                .specialistSelectionOfOrder(specialistSuggestion.getSpecialistSelectionOfOrder())
                .order(specialistSuggestion.getOrder())
                .proposedPrice(specialistSuggestion.getProposedPrice())
                .build();
    }

        public static Collection<SpecialistSuggestionDto>
            specialistSuggestionCollectionToSpecialistSuggestionCollectionDto(Collection<SpecialistSuggestion> specialistSuggestionCollection){
        Collection<SpecialistSuggestionDto> SpecialistSuggestionDtoCollection=new ArrayList<>();
        for (SpecialistSuggestion converter:specialistSuggestionCollection
        ) {
            SpecialistSuggestionDtoCollection.add(specialistSuggestionToSpecialistsuggestionDto(converter)) ;
        }
        return SpecialistSuggestionDtoCollection;
    }

}

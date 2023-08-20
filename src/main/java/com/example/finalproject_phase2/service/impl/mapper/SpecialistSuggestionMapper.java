package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.mapstruct.Mapper;

@Mapper
public interface SpecialistSuggestionMapper {
    SpecialistSuggestion convertDtoToEntity(SpecialistSuggestionDto specialistSuggestionDto);
    SpecialistSuggestionDto convertEntityToDto(SpecialistSuggestion specialistSuggestion);

}

package com.example.finalproject_phase2.service.impl.mapper;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
@Mapper(componentModel = "spring")
public interface SpecialistSuggestionMapper {
      SpecialistSuggestionDto specialistSuggestionToSpecialistsuggestionDto(SpecialistSuggestion specialistSuggestion);
    Collection<SpecialistSuggestionDto>
    specialistSuggestionCollectionToSpecialistSuggestionCollectionDto(Collection<SpecialistSuggestion> specialistSuggestionCollection);


}

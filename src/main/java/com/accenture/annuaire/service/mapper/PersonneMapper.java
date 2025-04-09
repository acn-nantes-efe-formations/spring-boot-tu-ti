package com.accenture.annuaire.service.mapper;


import com.accenture.annuaire.repository.entity.Personne;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonneMapper {

    Personne toEntity(PersonneRequestDto dto);
    PersonneResponseDto toResponseDto(Personne personne);
}

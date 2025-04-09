package com.accenture.annuaire.service;

import com.accenture.annuaire.exception.PersonneException;
import com.accenture.annuaire.repository.PersonneDao;
import com.accenture.annuaire.repository.entity.Personne;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import com.accenture.annuaire.service.mapper.PersonneMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneManager {

    PersonneDao pDao;
    PersonneMapper pMapper;

    @Value(value = "${annee}")
    private Integer annee;


    public PersonneManager(PersonneDao pDao, PersonneMapper pMapper) {
        this.pDao = pDao;
        this.pMapper = pMapper;
    }


    public PersonneResponseDto ajout(PersonneRequestDto dto) throws PersonneException {
        if (dto == null)
            throw new PersonneException("Personne nul");
        Personne enreg = pDao.save(pMapper.toEntity(dto));
        return pMapper.toResponseDto(enreg);
    }

    public PersonneResponseDto trouver(int id) {
        Optional<Personne> trouveOpt = pDao.findById(id);
        return trouveOpt.map(pMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Personne non trouvée : " + id));
    }

    public List<PersonneResponseDto> liste() {
        return pDao.findAll()
                .stream()
                .map(pMapper::toResponseDto)
                .toList();
    }

    public Integer getAnnee() {
        return annee;
    }


}

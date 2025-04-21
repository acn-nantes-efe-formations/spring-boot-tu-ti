package com.accenture.annuaire.service;


import com.accenture.annuaire.exception.PersonneException;
import com.accenture.annuaire.repository.PersonneDao;
import com.accenture.annuaire.repository.entity.Personne;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import com.accenture.annuaire.service.mapper.PersonneMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Tests unitaires de la classe PersonneManager avec AssertJ et Mockito
@ExtendWith(MockitoExtension.class)
 class PersonneManagerAssertJUnitTest {

    @Mock
    private PersonneDao daoMock;
    @Mock
    private PersonneMapper pMapper;

    @InjectMocks
    private PersonneManager manager;

    @Captor
    private ArgumentCaptor<Personne> captor;

    @Test
    void testAjoutNull() {
        assertThatThrownBy(() -> manager.ajout(null))
                .isInstanceOf(PersonneException.class)
                .hasMessage("Personne nul");
    }

    @Test
    void testAjoutPersonneNonNul()  {
        PersonneRequestDto dto = new PersonneRequestDto("Lepetit", "Joe", 44);
        Personne p = new Personne(0, "Lepetit", "Joe", 44);
        Mockito.when(pMapper.toEntity(dto)).thenReturn(p);

        assertThatCode(() -> manager.ajout(dto)).doesNotThrowAnyException();

        Mockito.verify(daoMock).save(captor.capture());
        assertThat(captor.getValue()).isSameAs(p);
    }

    @Test
    void testTrouverPasDeData(){
        Mockito.when(daoMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> manager.trouver(5))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void testTrouverAvecData(){
        Personne p = new Personne(0, "Lepetit", "Joe", 44);
        PersonneResponseDto dto = new PersonneResponseDto(5, "Lepetit", "Joe", 44);
        Optional<Personne> optPers = Optional.of(p);
        Mockito.when(daoMock.findById(Mockito.anyInt())).thenReturn(optPers);
        Mockito.when(pMapper.toResponseDto(Mockito.any())).thenReturn(dto);
        PersonneResponseDto trouve = manager.trouver(5);
        assertThat(trouve)
                .isNotNull()
                .isSameAs(dto);


    }

    @Test
    void testGetValue(){
        ReflectionTestUtils.setField(manager, "annee", 2023);
        int annee = manager.getAnnee();
        assertThat(annee).isEqualTo(2023);
    }


    @Test
    void testListe() {
        // Mock des données
        List<Personne> personnes = List.of(
                new Personne(1, "Dupont", "Jean", 30),
                new Personne(2, "Durand", "Marie", 25)
        );
        List<PersonneResponseDto> dtos = List.of(
                new PersonneResponseDto(1, "Dupont", "Jean", 30),
                new PersonneResponseDto(2, "Durand", "Marie", 25)
        );

        // Simulation des comportements
        Mockito.when(daoMock.findAll()).thenReturn(personnes);
        Mockito.when(pMapper.toResponseDto(personnes.get(0))).thenReturn(dtos.get(0));
        Mockito.when(pMapper.toResponseDto(personnes.get(1))).thenReturn(dtos.get(1));

        // Appel de la méthode
        List<PersonneResponseDto> result = manager.liste();

        // Vérifications
        assertThat(result).isNotNull().hasSize(2).isEqualTo(dtos);
    }
}

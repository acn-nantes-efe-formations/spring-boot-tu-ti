package com.accenture.annuaire.service;


import com.accenture.annuaire.exception.PersonneException;
import com.accenture.annuaire.repository.PersonneDao;
import com.accenture.annuaire.repository.entity.Personne;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import com.accenture.annuaire.service.mapper.PersonneMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


// Test unitaires de la classe PersonneManager avec JUnit5 et Mockito
@ExtendWith(MockitoExtension.class)
 class PersonneManagerUnitTest {

    @Mock
    private PersonneDao daoMock;
    @Mock
    private PersonneMapper pMapper;

    @InjectMocks
    private PersonneManager manager;

    @Captor
    private ArgumentCaptor<Personne> captor;

    @Test
    void testAjoutNull(){
        PersonneException e = Assertions.assertThrows(PersonneException.class, () -> manager.ajout(null));
        Assertions.assertEquals("Personne nul", e.getMessage());
    }

    @Test
    void testAjoutPersonneNonNul()  {
        PersonneRequestDto dto = new PersonneRequestDto("Lepetit", "Joe", 44);
        Personne p = new Personne(0, "Lepetit", "Joe", 44);
        Mockito.when(pMapper.toEntity(dto)).thenReturn(p);

        Assertions.assertDoesNotThrow(() -> manager.ajout(dto));

        Mockito.verify(daoMock).save(captor.capture());
        Assertions.assertSame(p, captor.getValue());
    }

    @Test
    void testTrouverPasDeData(){
        Mockito.when(daoMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> manager.trouver(5));

    }

    @Test
    void testTrouverAvecData(){
        Personne p = new Personne(0, "Lepetit", "Joe", 44);
        PersonneResponseDto dto = new PersonneResponseDto(5, "Lepetit", "Joe", 44);
        Optional<Personne> optPers = Optional.of(p);
        Mockito.when(daoMock.findById(Mockito.anyInt())).thenReturn(optPers);
        Mockito.when(pMapper.toResponseDto(Mockito.any())).thenReturn(dto);
        PersonneResponseDto trouve = manager.trouver(5);
        assertAll(
                () -> Assertions.assertNotNull(trouve),
                () -> assertSame(dto, trouve)
        );

    }

    @Test
    void testGetValue(){
        ReflectionTestUtils.setField(manager, "annee", 2023);
        int annee = manager.getAnnee();
        Assertions.assertEquals(2023, annee);
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(dtos, result);
    }
}

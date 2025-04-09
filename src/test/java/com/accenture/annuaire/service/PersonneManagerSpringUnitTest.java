package com.accenture.annuaire.service;


import com.accenture.annuaire.repository.PersonneDao;
import com.accenture.annuaire.repository.entity.Personne;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import com.accenture.annuaire.service.mapper.PersonneMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Tests d'intégration "légers" sur le service PersonneManager
@SpringBootTest(classes = {PersonneManager.class})
class PersonneManagerSpringUnitTest {

	@MockitoBean
	private PersonneDao pDao;

	@MockitoBean
	private PersonneMapper pMapper;

	@Autowired
	private PersonneManager personneManager;
	
	@Captor
	private ArgumentCaptor<Personne> captor;
	
	
	@Test
	void testAjoutPersonneNulle()  {
		Exception e = assertThrows(Exception.class, () -> personneManager.ajout(null));
		assertEquals("Personne nul", e.getMessage());
	}
	
	@Test
	void testAjoutPersonne() {
		PersonneRequestDto dto = new PersonneRequestDto("Lepetit", "Joe", 44);
		Personne p = new Personne(0, "Lepetit", "Joe", 44);
		Mockito.when(pMapper.toEntity(dto)).thenReturn(p);
		assertDoesNotThrow(() -> personneManager.ajout(dto));
		Mockito.verify(pDao).save(captor.capture());
		assertSame(p, captor.getValue());
	}
	
	@Test
	void testTrouvePersonneExistante() {
		Personne p = new Personne(0, "Lepetit", "Joe", 44);
		PersonneResponseDto dto = new PersonneResponseDto(5, "Lepetit", "Joe", 44);
		Optional<Personne> optPers = Optional.of(p);
		Mockito.when(pDao.findById(Mockito.anyInt())).thenReturn(optPers);
		Mockito.when(pMapper.toResponseDto(Mockito.any())).thenReturn(dto);
		PersonneResponseDto trouve = personneManager.trouver(5);
		assertAll(
				() -> Assertions.assertNotNull(trouve),
				() -> assertSame(dto, trouve)
				);
	}
	
	@Test
	void testTrouvePersonneInexistante() {
		Mockito.when(pDao.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> personneManager.trouver(5));
	}
	
	@Test
	void testListe() {
		Personne p1 = new Personne(1, "Legrand", "Joe", 44);
		Personne p2 = new Personne(2, "Lepetit", "Jack", 55);
		List<Personne> listeP =List.of(p1,p2);

		PersonneResponseDto dto1 = new PersonneResponseDto(1, "Legrand", "Joe", 44);
		PersonneResponseDto dto2 = new PersonneResponseDto(2, "Lepetit", "Jack", 55);
		List<PersonneResponseDto> listeDto = List.of(dto1, dto2);

		Mockito.when(pMapper.toResponseDto(p1)).thenReturn(dto1);
		Mockito.when(pMapper.toResponseDto(p2)).thenReturn(dto2);

		Mockito.when(pDao.findAll()).thenReturn(listeP);
		
		assertEquals(listeDto, personneManager.liste());
	}

	@Test
	void testGetAnnee() {

		assertEquals(2022, personneManager.getAnnee());
	}

	
}

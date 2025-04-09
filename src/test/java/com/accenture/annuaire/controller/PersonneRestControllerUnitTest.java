package com.accenture.annuaire.controller;

import com.accenture.annuaire.service.PersonneManager;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


// Tests unitaires de la classe PersonneRestController
@ExtendWith(MockitoExtension.class)
 class PersonneRestControllerUnitTest {

	@InjectMocks
	private PersonneRestController controller;
	
	@Mock
	private PersonneManager pm;
	
	
	@Test
	void testGetPersonnePasTrouvee() {
		Mockito.when(pm.trouver(Mockito.anyInt())).thenThrow(new EntityNotFoundException());
		assertThrows(EntityNotFoundException.class, () -> controller.getPersonne(5));
	}
	
	@Test
	void testGetPersonneTrouvee() {

		PersonneResponseDto p = new PersonneResponseDto(5, "Lepetit", "Joe", 44);
		Mockito.when(pm.trouver(Mockito.anyInt())).thenReturn(p);
		
		ResponseEntity<PersonneResponseDto> re = controller.getPersonne(5);
		assertEquals(p, re.getBody());
		assertTrue(re.getStatusCode().is2xxSuccessful());
	}

}

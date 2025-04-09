	package com.accenture.annuaire.controller;

    import com.accenture.annuaire.service.PersonneManager;
    import jakarta.persistence.EntityNotFoundException;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;

    import static org.junit.Assert.assertThrows;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.Mockito.when;

	// Classe de test d'intégration léger pour le contrôleur PersonneRestController
	@SpringBootTest(classes = {PersonneRestController.class})
	 class PersonneRestControllerSpringUnitTest {
	
		@Autowired
		PersonneRestController prc;
		
		@MockitoBean
		PersonneManager pm;
		
	
		@Test
		void testPersonnePasTrouvee(){

			when(pm.trouver(anyInt())).thenThrow(new EntityNotFoundException());
			
			assertThrows(EntityNotFoundException.class, () -> prc.getPersonne(1));
	        
	 	}
	
		
	}


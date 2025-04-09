	package com.accenture.annuaire.controller;

    import com.accenture.annuaire.service.dto.PersonneResponseDto;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.test.web.server.LocalServerPort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

    import static org.junit.jupiter.api.Assertions.assertEquals;


	// Classe de test de bout en bout pour le contrôleur REST PersonneRestController.

    @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	 class PersonneRestControllerEndToEndTest {

		
		@LocalServerPort
		private int port;

		@Autowired
		private TestRestTemplate restTemplate;
		
		@Test
		void testTrouve()  {
			ResponseEntity<PersonneResponseDto> response = restTemplate.
					  getForEntity("http://localhost:" + port +  "/personnes/1", PersonneResponseDto.class);
			assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
		}
		
		@Test
		void testTrouve2() {
			PersonneResponseDto result = this.restTemplate
					.getForObject("http://localhost:" + port +  "/personnes/1", PersonneResponseDto.class);
			System.out.println(result);
			assertEquals("Legrand", result.nom());
		}
		
		@Test
		void testPasTrouve(){
			ResponseEntity<PersonneResponseDto> response = restTemplate.
					  getForEntity("http://localhost:" + port +  "/personnes/111", PersonneResponseDto.class);
			assertEquals(response.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
		}
		
		@Test
		void testMauvaiseUrl() {
			ResponseEntity<PersonneResponseDto> response = restTemplate.
					getForEntity("http://localhost:" + port +  "/personnes/Hello", PersonneResponseDto.class);
			assertEquals(response.getStatusCode().value(), HttpStatus.BAD_REQUEST.value());
		}
		
	}

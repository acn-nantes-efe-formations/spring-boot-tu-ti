package com.accenture.annuaire.controller;

import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



// Classe de test d'intégration de PersonneRestController

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonneRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetPersonnesQuantite() throws Exception {
	    mockMvc
	        .perform(get("/personnes"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()").value(3));
	}


	@Test
	void testGetPersonnePasTrouvee() throws Exception {
		mockMvc
			.perform(get("/personnes/5"))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	void testGetPersonneTrouvee() throws Exception {
	String jsonContent = """
		{
			"id": 2,
			"nom": "Lemoyen",
			"prenom": "Marc",
			"age": 35
		}
	""";
		mockMvc
	        .perform(get("/personnes/2"))
	        .andExpect(status().isOk())
	        .andExpect(content().json(jsonContent));
	}

@Test
void testAjouterPersonne() throws Exception {
    String jsonContent = """
        {
            "nom": "Lemoyen",
            "prenom": "Marc",
            "age": 35
        }
    """;
    mockMvc
        .perform(post("/personnes")
            .contentType("application/json")
            .content(jsonContent))
        .andExpect(status().isCreated());
}


	@Test
	void testAjouterPersonneAvecObjectMapper() throws Exception {
		PersonneRequestDto dto = new PersonneRequestDto("Lemoyen", "Marc", 35);
		mockMvc
				.perform(post("/personnes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isCreated());
	}

@Test
void testAjouterPersonneEnEchec() throws Exception {
    String jsonContent = """
        {
            "nom": "Lemoyen",
            "age": 35
        }
    """;
    mockMvc
        .perform(post("/personnes")
            .contentType("application/json")
            .content(jsonContent))
        .andExpect(status().isBadRequest());
}
}

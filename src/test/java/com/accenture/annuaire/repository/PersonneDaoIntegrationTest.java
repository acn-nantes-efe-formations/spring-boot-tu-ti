package com.accenture.annuaire.repository;

import com.accenture.annuaire.repository.entity.Personne;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PersonneDaoIntegrationTest {

	@Autowired
	PersonneDao pDao;

	@Test
	@Sql("/createUser.sql")
	void testListe() {
		List<Personne> liste = pDao.trouverTous();
		assertEquals(5, liste.size());
	}

	@Test
	@Sql("/createUser.sql")
	void testTrouverParNom() {
		List<Personne> liste = pDao.trouverParNom("Legrand");
		assertAll(
				() -> assertEquals(2, liste.size(), "Il doit y avoir 2 personnes avec le nom 'Legrand'"),
				() -> assertTrue(liste.stream().allMatch(p -> p.getNom().equals("Legrand")), "Tous les noms doivent être 'Legrand'")
		);
	}

	// Ne devrait pas exister
	@Test
	@Sql("/createUser.sql")
	void testAjout() {
		Personne p = new Personne("Leblond", "Luc", 41);
		Personne savedPerson = pDao.save(p);

		assertAll(
				() -> assertTrue(savedPerson.getId() > 0, "L'ID doit être généré"),
				() -> assertEquals("Leblond", savedPerson.getNom(), "Le nom doit correspondre"),
				() -> assertEquals("Luc", savedPerson.getPrenom(), "Le prénom doit correspondre"),
				() -> assertEquals(41, savedPerson.getAge(), "L'âge doit correspondre")
		);
	}

}

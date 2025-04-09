package com.accenture.annuaire.repository;

import com.accenture.annuaire.repository.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonneDao extends JpaRepository<Personne, Integer> {

	@Query("select p from Personne p order by p.nom")
	List<Personne> trouverTous();

	@Query("select p from Personne p WHERE p.nom = :nom")
	List<Personne> trouverParNom(String nom);

}

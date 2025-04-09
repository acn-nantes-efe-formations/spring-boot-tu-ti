package com.accenture.annuaire.controller;

import com.accenture.annuaire.service.PersonneManager;
import com.accenture.annuaire.service.dto.PersonneRequestDto;
import com.accenture.annuaire.service.dto.PersonneResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personnes")
public class PersonneRestController {

	PersonneManager pm;

	public PersonneRestController(PersonneManager pm) {
		this.pm = pm;
	}

	@GetMapping
	public ResponseEntity<List<PersonneResponseDto>> getPersonnes() {
		return new ResponseEntity<>(pm.liste(), HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonneResponseDto> getPersonne(@PathVariable("id") Integer id) {
		PersonneResponseDto p = pm.trouver(id);
		return ResponseEntity.ok(p);
	}

	@PostMapping
	public ResponseEntity<PersonneResponseDto> postPersonne(@Valid @RequestBody PersonneRequestDto dto) {
		PersonneResponseDto nouveau = pm.ajout(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
	}

}



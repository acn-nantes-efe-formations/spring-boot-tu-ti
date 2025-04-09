package com.accenture.annuaire.service.dto;

public record PersonneResponseDto(
        int id,
        String nom,
        String prenom,
        int age
        ) {}

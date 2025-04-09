package com.accenture.annuaire.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonneRequestDto(
        @NotBlank
        @NotNull
        String nom,

        @NotNull
        @NotBlank
        String prenom,

        @NotNull
        int age
        ) {}

package com.abutua.agenda.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequest(
    @NotBlank(message = "Nome requirido") String name,
    @NotBlank(message = "Telefone requirido") String phone,
    @NotNull(message = "Data de nascimento requirida") LocalDate dateOfBirth) {
}
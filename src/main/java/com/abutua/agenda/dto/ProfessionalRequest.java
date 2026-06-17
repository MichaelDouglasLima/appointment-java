package com.abutua.agenda.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfessionalRequest(
    @NotBlank(message = "Nome requirido") String name,
    @NotBlank(message = "Telefone requirido") String phone,
    @NotNull(message = "Ativo não pode ser nulo") Boolean active,
    @Valid List<IntegerDTO> areas) {

}

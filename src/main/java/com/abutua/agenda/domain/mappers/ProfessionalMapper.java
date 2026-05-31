package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.dto.ProfessionalResponse;

public class ProfessionalMapper {

  public static ProfessionalResponse toProfessionalResponseDTO(Professional professional) {
    return new ProfessionalResponse(professional.getId(), professional.getName(), professional.getPhone(),
        professional.isActive());
  }
}

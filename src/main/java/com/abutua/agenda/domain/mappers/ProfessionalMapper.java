package com.abutua.agenda.domain.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;

public class ProfessionalMapper {

  public static ProfessionalResponse toProfessionalResponseDTO(Professional professional) {
    return new ProfessionalResponse(professional.getId(), professional.getName(), professional.getPhone(),
        professional.isActive());
  }

  public static Professional fromProfessionalRequestDTO(ProfessionalRequest professionalRequest) {
    Professional professional = new Professional(
        professionalRequest.name(),
        professionalRequest.phone(),
        professionalRequest.active());

    if (professionalRequest.areas() != null) {
      Set<Area> areas = professionalRequest.areas().stream()
          .map(a -> new Area(a.id()))
          .collect(Collectors.toSet());
      professional.setAreas(areas);
    }

    return professional;
  }
}

package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.dto.AreaResponse;

public class AreaMapper {

  public static AreaResponse toAreaResponseDTO(Area area) {
    return new AreaResponse(area.getId(), area.getName());
  }
}

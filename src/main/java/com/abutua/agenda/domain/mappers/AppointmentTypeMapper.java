package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.dto.AppointmentTypeResponse;

public class AppointmentTypeMapper {

  public static AppointmentTypeResponse toAppointmentTypeResponseDTO(AppointmentType appointmentType) {
    return new AppointmentTypeResponse(appointmentType.getId(), appointmentType.getType());
  }
}

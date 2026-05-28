package com.abutua.agenda.domain.mappers;

import org.springframework.beans.BeanUtils;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;
import com.abutua.agenda.dto.IntegerDTO;
import com.abutua.agenda.dto.LongDTO;

public class AppointmentMapper {
  public static AppointmentResponse toAppointmentResponseDTO(Appointment appointment) {
    AppointmentResponse appointmentResponse = new AppointmentResponse(
        appointment.getId(),
        appointment.getDate(),
        appointment.getStartTime(),
        appointment.getEndTime(),
        appointment.getComments(),
        appointment.getStatus(),
        new IntegerDTO(appointment.getAppointmentType().getId()),
        new IntegerDTO(appointment.getArea().getId()),
        new LongDTO(appointment.getProfessional().getId()),
        new LongDTO(appointment.getClient().getId()));

    return appointmentResponse;
  }

  public static Appointment fromAppointmentRequestDTO(AppointmentRequest appointmentRequest) {
    Appointment appointment = new Appointment();

    BeanUtils.copyProperties(appointmentRequest, appointment);

    appointment.setArea(new Area(appointmentRequest.area().id()));
    appointment.setAppointmentType(new AppointmentType(appointmentRequest.type().id()));
    appointment.setProfessional(new Professional(appointmentRequest.professional().id()));
    appointment.setClient(new Client(appointmentRequest.client().id()));

    return appointment;
  }
}

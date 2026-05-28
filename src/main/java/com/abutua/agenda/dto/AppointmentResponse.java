package com.abutua.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.abutua.agenda.domain.entities.AppointmentStatus;

public record AppointmentResponse(
    long id,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    String comments,
    AppointmentStatus status,
    IntegerDTO type,
    IntegerDTO area,
    LongDTO professional,
    LongDTO client) {

}

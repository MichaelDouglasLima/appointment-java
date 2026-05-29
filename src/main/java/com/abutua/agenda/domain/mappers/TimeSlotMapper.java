package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.dto.TimeSlotResponse;

public class TimeSlotMapper {

  public static TimeSlotResponse toTimeSlotResponseDTO(TimeSlot timeSlot) {
    return new TimeSlotResponse(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.isAvailable());
  }
}

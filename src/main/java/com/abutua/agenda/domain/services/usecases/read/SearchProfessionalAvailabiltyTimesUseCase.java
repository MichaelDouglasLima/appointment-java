package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentStatus;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.entities.WorkScheduleItem;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.WorkScheduleItemRepository;

@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

  @Autowired
  private WorkScheduleItemRepository workScheduleItemRepository;

  @Autowired
  private AppointmentRepository appointmentRepository;

  public List<TimeSlot> executeUseCase(Professional professional, LocalDate date) {

    List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
    List<WorkScheduleItem> workScheduleItems = getWorkScheduleItems(professional, date);
    List<Appointment> appointments = getAppointments(professional, date);

    for (var item : workScheduleItems) {

      timeSlots.addAll(this.calculateTimeSlots(item, appointments, date));
    }

    return timeSlots;
  }

  private List<TimeSlot> calculateTimeSlots(WorkScheduleItem item, List<Appointment> appointments, LocalDate date) {
    var startTime = item.getStartTime();
    var slotSize = item.getSlotSize();
    var slots = item.getSlots();

    List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

    for (int i = 0; i < slots; i++) {
      var start = startTime.plusMinutes(i * slotSize);
      var end = start.plusMinutes(slotSize);

      boolean available = this.isTimeSlotAvailable(start, end, appointments, date);

      TimeSlot timeSlot = new TimeSlot(start, end, available);

      timeSlots.add(timeSlot);
    }

    return timeSlots;
  }

  private boolean isTimeSlotAvailable(LocalTime start, LocalTime end, List<Appointment> appointments, LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      return false;
    } else if (date.isEqual(LocalDate.now()) && start.isBefore(LocalTime.now())) {
      return false;
    }

    return appointments.stream().noneMatch(
        a -> a.getStartTime().isBefore(end) &&
            a.getEndTime().isAfter(start) &&
            (a.getStatus().equals(AppointmentStatus.OPEN) ||
                a.getStatus().equals(AppointmentStatus.PRESENT)));
  }

  private List<Appointment> getAppointments(Professional professional, LocalDate date) {
    return this.appointmentRepository.findByProfessionalIdAndDate(professional.getId(), date);
  }

  private List<WorkScheduleItem> getWorkScheduleItems(Professional professional, LocalDate date) {
    return this.workScheduleItemRepository
        .getWorkScheduleFromProfessionalByDayOfWeekOrderByStartTime(professional,
            date.getDayOfWeek());
  }
}

package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;

@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

  @Autowired
  private AppointmentRepository appointmentRepository;

  public List<TimeSlot> executeUseCase(long professionalId, LocalDate date) {
    return this.appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
  }
}

// @Service
// public class SearchProfessionalAvailabiltyTimesUseCase {

// @Autowired
// private WorkScheduleItemRepository workScheduleItemRepository;

// @Autowired
// private AppointmentRepository appointmentRepository;

// public List<TimeSlot> executeUseCase(Professional professional, LocalDate
// date) {

// List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
// List<WorkScheduleItem> workScheduleItems = getWorkScheduleItems(professional,
// date);
// List<Appointment> appointments = getAppointments(professional, date);

// for (var item : workScheduleItems) {

// timeSlots.addAll(this.calculateTimeSlots(item, appointments, date));
// }

// return timeSlots;
// }

// private List<TimeSlot> calculateTimeSlots(WorkScheduleItem item,
// List<Appointment> appointments, LocalDate date) {
// var startTime = item.getStartTime();
// var slotSize = item.getSlotSize();
// var slots = item.getSlots();

// List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

// for (int i = 0; i < slots; i++) {
// var start = startTime.plusMinutes(i * slotSize);
// var end = start.plusMinutes(slotSize);

// boolean available = this.isTimeSlotAvailable(start, end, appointments);
// boolean nowOrFuture = this.isStartTimeValidIfDateIsToday(start, date);

// TimeSlot timeSlot = new TimeSlot(start, end, available && nowOrFuture);

// timeSlots.add(timeSlot);
// }

// return timeSlots;
// }

// private boolean isTimeSlotAvailable(LocalTime start, LocalTime end,
// List<Appointment> appointments) {

// return appointments.stream().noneMatch(
// a -> a.getStartTime().isBefore(end) &&
// a.getEndTime().isAfter(start) &&
// (a.getStatus().equals(AppointmentStatus.OPEN) ||
// a.getStatus().equals(AppointmentStatus.PRESENT)));
// }

// private List<Appointment> getAppointments(Professional professional,
// LocalDate date) {
// return
// this.appointmentRepository.findByProfessionalIdAndDate(professional.getId(),
// date);
// }

// private List<WorkScheduleItem> getWorkScheduleItems(Professional
// professional, LocalDate date) {
// return this.workScheduleItemRepository
// .getWorkScheduleFromProfessionalByDayOfWeekOrderByStartTime(professional,
// date.getDayOfWeek());
// }

// private boolean isStartTimeValidIfDateIsToday(LocalTime start, LocalDate
// date) {
// return date.isAfter(LocalDate.now()) || (date.equals(LocalDate.now()) &&
// start.isAfter(LocalTime.now()));
// }
// }

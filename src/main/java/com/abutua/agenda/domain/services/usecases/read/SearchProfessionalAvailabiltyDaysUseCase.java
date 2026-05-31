package com.abutua.agenda.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.repositories.AppointmentRepository;

@Service
public class SearchProfessionalAvailabiltyDaysUseCase {

  @Autowired
  private AppointmentRepository appointmentRepository;

  public List<Integer> executeUseCase(Long professionalId, LocalDate start, LocalDate end) {
    return this.appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
  }
}

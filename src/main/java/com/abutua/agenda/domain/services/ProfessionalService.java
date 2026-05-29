package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

  @Autowired
  private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

  @Autowired
  private ProfessionalRepository professionalRepository;

  public List<TimeSlot> searchProfessionalAvailabilty(Long professionalId, LocalDate date) {
    Professional professional = this.professionalRepository.findById(professionalId)
        .orElseThrow(() -> new EntityNotFoundException("Professional não encontrado!"));

    List<TimeSlot> timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional, date);

    return timeSlots;
  }
}

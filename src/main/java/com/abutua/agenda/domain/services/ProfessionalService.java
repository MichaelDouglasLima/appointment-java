package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.mappers.TimeSlotMapper;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.dto.TimeSlotResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

  @Autowired
  private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

  @Autowired
  private ProfessionalRepository professionalRepository;

  public List<TimeSlotResponse> getAvailabilityTimes(Long professionalId, LocalDate date) {
    Professional professional = this.professionalRepository.findById(professionalId)
        .orElseThrow(() -> new EntityNotFoundException("Professional não encontrado!"));

    List<TimeSlot> timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional, date);

    return timeSlots.stream().map(ts -> TimeSlotMapper.toTimeSlotResponseDTO(ts)).collect((Collectors.toList()));
  }
}

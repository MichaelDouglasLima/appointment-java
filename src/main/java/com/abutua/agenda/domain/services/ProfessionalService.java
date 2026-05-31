package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.mappers.TimeSlotMapper;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.ParameterException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyDaysUseCase;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.dto.TimeSlotResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {

  @Autowired
  private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

  @Autowired
  private SearchProfessionalAvailabiltyDaysUseCase searchProfessionalAvailabiltyDaysUseCase;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Transactional(readOnly = true)
  public List<TimeSlotResponse> getAvailabilityTimesFromProfessional(long professionalId, LocalDate date) {
    Professional professional = getProfessional(professionalId);
    List<TimeSlot> timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional.getId(),
        date);

    return timeSlots.stream().map(ts -> TimeSlotMapper.toTimeSlotResponseDTO(ts)).collect((Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public List<Integer> getAvailabilityDaysFromProfessional(long professionalId, int month, int year) {
    checkProfessionalExistsOrThrowsException(professionalId);
    checkMonthIsValidOrThrowsException(month);
    checkYearIsValidOrThrowsException(year);
    checkMonthAndCurrentYearIsValidOrThrowsException(month, year);

    LocalDate start = LocalDate.of(year, month, 1);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

    return this.searchProfessionalAvailabiltyDaysUseCase.executeUseCase(professionalId, start, end);
  }

  private void checkProfessionalExistsOrThrowsException(long professionalId) {
    if (!this.professionalRepository.existsById(professionalId)) {
      throw new EntityNotFoundException("Professional não encontrado!");
    }
  }

  private Professional getProfessional(long professionalId) {
    return this.professionalRepository.findById(professionalId)
        .orElseThrow(() -> new EntityNotFoundException("Professional não encontrado!"));
  }

  private void checkMonthAndCurrentYearIsValidOrThrowsException(int month, int year) {
    if (year == LocalDate.now().getYear() && month < LocalDate.now().getMonthValue()) {
      throw new ParameterException("Mês inválido! O mês deve igual ou superior ao mês do ano corrente!");
    }
  }

  private void checkYearIsValidOrThrowsException(int year) {
    if (year < LocalDate.now().getYear()) {
      throw new ParameterException("Ano inválido! O ano deve ser igual ou superior ao ano corrente!");
    }
  }

  private void checkMonthIsValidOrThrowsException(int month) {
    if (month < 1 || month > 12) {
      throw new ParameterException("Mês inválido. O valor do mês deve ser de 1 a 12.");
    }
  }
}

package com.abutua.agenda.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.mappers.AreaMapper;
import com.abutua.agenda.domain.mappers.ProfessionalMapper;
import com.abutua.agenda.domain.mappers.TimeSlotMapper;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.domain.services.exceptions.ParameterException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyDaysUseCase;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;
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

  @Autowired
  private AreaRepository areaRepository;

  @Autowired
  private AppointmentRepository appointmentRepository;

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

  @Transactional
  public ProfessionalResponse save(ProfessionalRequest professionalRequest) {

    if (professionalRequest.areas() != null) {
      for (var areaDto : professionalRequest.areas()) {
        if (!areaRepository.existsById(areaDto.id())) {
          throw new EntityNotFoundException(
              "Área não encontrada para vincular ao profissional! id da área: " + areaDto.id());
        }
      }
    }

    var professional = this.professionalRepository
        .save(ProfessionalMapper.fromProfessionalRequestDTO(professionalRequest));

    return ProfessionalMapper.toProfessionalResponseDTO(professional);
  }

  @Transactional(readOnly = true)
  public ProfessionalResponse getById(long id) {
    var professional = this.professionalRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado!"));

    return ProfessionalMapper.toProfessionalResponseDTO(professional);
  }

  @Transactional(readOnly = true)
  public Page<ProfessionalResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Professional> pageProfessional = this.professionalRepository.findByNameContainingIgnoreCase(name,
        pageRequest);
    return pageProfessional.map(p -> ProfessionalMapper.toProfessionalResponseDTO(p));
  }

  @Transactional(readOnly = true)
  public Set<AreaResponse> getProfessionalAreas(long id) {
    Professional professional = this.professionalRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado!"));
    return professional.getAreas().stream().map(a -> AreaMapper.toAreaResponseDTO(a)).collect(Collectors.toSet());
  }

  @Transactional
  public void updateById(long id, ProfessionalRequest professionalUpdate) {
    try {
      Professional professional = this.professionalRepository.getReferenceById(id);

      professional.setName(professionalUpdate.name());
      professional.setPhone(professionalUpdate.phone());
      professional.setActive(professionalUpdate.active());

      if (professionalUpdate.areas() != null) {
        if (professionalUpdate.areas().isEmpty()) {
          professional.getAreas().clear();
        } else {
          Set<Area> areas = professionalUpdate.areas().stream()
              .map(a -> new Area(a.id()))
              .collect(Collectors.toSet());
          professional.setAreas(areas);
        }
      }

      this.professionalRepository.save(professional);
    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Profissional não encontrado!");
    } catch (ObjectRetrievalFailureException e) {
      throw new EntityNotFoundException("Área(s) não encontrada para vincular ao profissional atualizado!");
    }
  }

  @Transactional
  public void deleteById(long id) {
    if (!professionalRepository.existsById(id)) {
      throw new EntityNotFoundException("Profissional não encontrado!");
    }

    if (appointmentRepository.existsByProfessional_Id(id)) {
      throw new DatabaseException("Profissional possuí agendamentos! Profissional não pode ser deletado!");
    }

    try {
      this.professionalRepository.deleteById(id);

    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Constraint violation, profissional não pode ser deletado!");
    }
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

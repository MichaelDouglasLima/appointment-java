package com.abutua.agenda.domain.services.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.BusinessException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateAppointmentUseCase {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private AppointmentTypeRepository appAppointmentTypeRepository;

  @Autowired
  private AreaRepository areaRepository;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

  @Transactional
  public Appointment executeUseCase(Appointment appointment) {
    checkAppointmentTypeExistsOrElseThrow(appointment.getAppointmentType());
    checkAreaExistsOrElseThrow(appointment.getArea());

    Professional professional = this.getProfessionalIfExistsOrThrowsException(appointment.getProfessional());
    checkProfessionalIsActiveOrThrowsException(professional);
    checkAssociationBetweenProfessionalAndAreaOrThrowsException(professional, appointment.getArea());

    checkProfessionalHasAvailableScheduleOrThorwsException(professional, appointment);

    checkAppointmentIsNowOrFutureOrThrowsException(appointment.getDate(), appointment.getStartTime());

    // checkProfessionalHasAvailableScheduleOrThorwsException(professional,
    // appointment);

    // checkAppointmentIsNowOrFutureOrThrowsException(appointment.getDate(),

    Client client = this.getClientIfExistsOrThrowsException(appointment.getClient());

    return save(appointment, client, professional);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  private Appointment save(Appointment appointment, Client client, Professional professional) {
    checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(professional, appointment);
    checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(client, appointment);
    return this.appointmentRepository.save(appointment);
  }

  private void checkAppointmentTypeExistsOrElseThrow(AppointmentType appointmentType) {
    if (!this.appAppointmentTypeRepository.existsById(appointmentType.getId())) {
      throw new EntityNotFoundException("Tipo de Agendamento não encontrado!");
    }
  }

  private void checkAreaExistsOrElseThrow(Area area) {
    if (!this.areaRepository.existsById(area.getId())) {
      throw new EntityNotFoundException("Area não encontrada!");
    }
  }

  private Professional getProfessionalIfExistsOrThrowsException(Professional professional) {
    return this.professionalRepository.findById(professional.getId())
        .orElseThrow(() -> new EntityNotFoundException("Profissional não cadastrado!"));
  }

  private void checkProfessionalIsActiveOrThrowsException(Professional professional) {
    if (!professional.isActive()) {
      throw new BusinessException("Profissional não está ativo!");
    }
  }

  private Client getClientIfExistsOrThrowsException(Client client) {
    return this.clientRepository.findById(client.getId())
        .orElseThrow(() -> new EntityNotFoundException("Cliente não cadastrado!"));
  }

  private void checkAssociationBetweenProfessionalAndAreaOrThrowsException(Professional professional, Area area) {
    if (!this.professionalRepository.existsAssocioationWithArea(professional.getId(), area.getId())) {
      throw new BusinessException("O profissional não atua na área selecionada.");
    }
  }

  private void checkAppointmentIsNowOrFutureOrThrowsException(LocalDate date, LocalTime startTime) {
    if (date.isBefore(LocalDate.now())) {
      throw new BusinessException("A data do agendamento está no passado.");
    } else {
      if (date.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
        throw new BusinessException("O horário do agendamento está no passado.");
      }
    }
  }

  private void checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(Professional professional,
      Appointment appointment) {
    if (this.appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(professional,
        appointment.getDate(),
        appointment.getStartTime(),
        appointment.getEndTime())) {
      throw new BusinessException("O profissional possui agendamentos em aberto para o dia e horário selecionado.");
    }
  }

  private void checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(Client client,
      Appointment appointment) {
    if (this.appointmentRepository.existsOpenOrPresentAppointmentsForClient(client,
        appointment.getDate(),
        appointment.getStartTime(),
        appointment.getEndTime())) {
      throw new BusinessException("O cliente possui agendamentos em aberto para o dia e horário selecionado.");
    }
  }

  private void checkProfessionalHasAvailableScheduleOrThorwsException(Professional professional,
      Appointment appointment) {
    var timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional.getId(),
        appointment.getDate());

    if (timeSlots.isEmpty()) {
      throw new BusinessException("O profissional não trabalha na data selecionada.");
    } else {
      var timeSlot = timeSlots.stream()
          .filter(ts -> ts.getStartTime().toLocalTime().equals(appointment.getStartTime()) &&
              ts.getEndTime().toLocalTime().equals(appointment.getEndTime()))
          .findFirst();

      if (timeSlot.isEmpty()) {
        throw new BusinessException("O professional não trabalha no horário selecionado.");
      }

      if (!timeSlot.get().isAvailable()) {
        throw new BusinessException("O professional não tem disponibilidade para o horário selecionado.");
      }

    }
  }

}

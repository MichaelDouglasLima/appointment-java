package com.abutua.agenda.unit.domain.services.usecases.write;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.abutua.agenda.domain.entities.Appointment;
import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.exceptions.BusinessException;
import com.abutua.agenda.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.abutua.agenda.domain.services.usecases.write.CreateAppointmentUseCase;
import com.abutua.agenda.unit.factory.TimeSlotFactory;

import static org.mockito.BDDMockito.*;
import static java.util.Optional.*;

// @EnabledIf(expression = "#{environment.acceptsProfiles('test')}", loadContext = true)
// @SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CreateAppointmentUseCaseTest {

  @Mock
  private AppointmentRepository appointmentRepository;

  @Mock
  private AppointmentTypeRepository appointmentTypeRepository;

  @Mock
  private AreaRepository areaRepository;

  @Mock
  private ProfessionalRepository professionalRepository;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

  @InjectMocks
  private CreateAppointmentUseCase createAppointmentUseCase;

  @Test
  public void executeUseCaseShouldPersistAppointment() {

    // Mocks dos objetos necessários
    AppointmentType appointmentType = mock(AppointmentType.class);
    given(appointmentTypeRepository.existsById(anyInt())).willReturn(true);

    Area area = mock(Area.class);
    given(areaRepository.existsById(anyInt())).willReturn(true);

    Professional professional = mock(Professional.class);
    given(professional.isActive()).willReturn(true);
    given(professionalRepository.findById(anyLong())).willReturn(of(professional));
    given(professionalRepository.existsAssociationWithArea(anyLong(), anyInt())).willReturn(true);

    Client client = mock(Client.class);
    given(clientRepository.findById(anyLong())).willReturn(of(client));

    given(appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(
        any(Professional.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).willReturn(false);

    given(appointmentRepository.existsOpenOrPresentAppointmentsForClient(
        any(Client.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).willReturn(false);

    var timeSlots = createTimeSlots(true);
    given(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any(LocalDate.class)))
        .willReturn(timeSlots);

    Appointment appointment = new Appointment();
    appointment.setId(1l);
    appointment.setDate(LocalDate.now().plusDays(1));
    appointment.setStartTime(LocalTime.parse("08:00:00"));
    appointment.setEndTime(LocalTime.parse("08:30:00"));
    appointment.setComments("No comments");
    appointment.setAppointmentType(appointmentType);
    appointment.setArea(area);
    appointment.setProfessional(professional);
    appointment.setClient(client);

    given(appointmentRepository.save(any(Appointment.class))).willReturn(appointment);

    createAppointmentUseCase.executeUseCase(appointment);

    verify(appointmentTypeRepository).existsById(any());
    verify(areaRepository).existsById(any());
    verify(professionalRepository).findById(any());
    verify(professionalRepository).existsAssociationWithArea(any(), any());
    verify(searchProfessionalAvailabiltyTimesUseCase).executeUseCase(anyLong(), any());
    verify(clientRepository).findById(any());
    verify(appointmentRepository).save(any());

  }

  private List<TimeSlot> createTimeSlots(boolean available) {
    return List.of(TimeSlotFactory.createTimeSlot("08:00:00-03:00", "08:30:00-03:00", available));
  }

  @Test
  public void executeUseCaseShouldThrowsBusinessException_WrongStartAndEndTime() {

    // Mocks dos objetos necessários
    AppointmentType appointmentType = mock(AppointmentType.class);
    lenient().when(appointmentTypeRepository.existsById(anyInt())).thenReturn(true);

    Area area = mock(Area.class);
    lenient().when(areaRepository.existsById(anyInt())).thenReturn(true);

    Professional professional = mock(Professional.class);
    lenient().when(professional.isActive()).thenReturn(true);
    lenient().when(professionalRepository.findById(anyLong())).thenReturn(of(professional));
    lenient().when(professionalRepository.existsAssociationWithArea(anyLong(), anyInt())).thenReturn(true);

    Client client = mock(Client.class);
    lenient().when(clientRepository.findById(anyLong())).thenReturn(of(client));

    lenient().when(appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(
        any(Professional.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).thenReturn(false);

    lenient().when(appointmentRepository.existsOpenOrPresentAppointmentsForClient(
        any(Client.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).thenReturn(false);

    var timeSlots = createTimeSlots(true);
    given(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any(LocalDate.class)))
        .willReturn(timeSlots);

    Appointment appointment = new Appointment();
    appointment.setId(1l);
    appointment.setDate(LocalDate.of(2024, 4, 8));
    appointment.setStartTime(LocalTime.parse("08:15:00"));
    appointment.setEndTime(LocalTime.parse("08:30:00"));
    appointment.setComments("No comments");
    appointment.setAppointmentType(appointmentType);
    appointment.setArea(area);
    appointment.setProfessional(professional);
    appointment.setClient(client);

    // given(appointmentRepository.save(any(Appointment.class))).willReturn(appointment);

    assertThrows(BusinessException.class, () -> createAppointmentUseCase.executeUseCase(appointment));

    verify(searchProfessionalAvailabiltyTimesUseCase).executeUseCase(anyLong(), any());
    verify(appointmentRepository, never()).save(any());
  }

  @Test
  public void executeUseCaseShouldThrowsBusinessException_ProfessionalNotAvailable() {
    // Mocks dos objetos necessários
    AppointmentType appointmentType = mock(AppointmentType.class);
    lenient().when(appointmentTypeRepository.existsById(anyInt())).thenReturn(true);

    Area area = mock(Area.class);
    lenient().when(areaRepository.existsById(anyInt())).thenReturn(true);

    Professional professional = mock(Professional.class);
    lenient().when(professional.isActive()).thenReturn(true);
    lenient().when(professionalRepository.findById(anyLong())).thenReturn(of(professional));
    lenient().when(professionalRepository.existsAssociationWithArea(anyLong(), anyInt())).thenReturn(true);

    Client client = mock(Client.class);
    lenient().when(clientRepository.findById(anyLong())).thenReturn(of(client));

    lenient().when(appointmentRepository.existsOpenOrPresentAppointmentsForClient(
        any(Client.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).thenReturn(false);

    given(appointmentRepository.existsOpenOrPresentAppointmentsForProfessional(
        any(Professional.class),
        any(LocalDate.class),
        any(LocalTime.class),
        any(LocalTime.class))).willReturn(true);

    var timeSlots = createTimeSlots(true);
    given(searchProfessionalAvailabiltyTimesUseCase.executeUseCase(anyLong(), any(LocalDate.class)))
        .willReturn(timeSlots);

    Appointment appointment = new Appointment();
    appointment.setId(1l);
    appointment.setDate(LocalDate.now().plusDays(1));
    appointment.setStartTime(LocalTime.parse("08:00:00"));
    appointment.setEndTime(LocalTime.parse("08:30:00"));
    appointment.setComments("No comments");
    appointment.setAppointmentType(appointmentType);
    appointment.setArea(area);
    appointment.setProfessional(professional);
    appointment.setClient(client);

    assertThrows(BusinessException.class, () -> createAppointmentUseCase.executeUseCase(appointment));

    verify(searchProfessionalAvailabiltyTimesUseCase).executeUseCase(anyLong(), any());
    verify(appointmentRepository, never()).save(any());

  }

}
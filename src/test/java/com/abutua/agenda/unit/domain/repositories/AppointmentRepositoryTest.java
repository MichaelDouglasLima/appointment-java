package com.abutua.agenda.unit.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@EnabledIf(expression = "#{environment.acceptsProfiles('test')}", loadContext = true)
@SpringBootTest
public class AppointmentRepositoryTest {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Test
  void existsOpenOrPresentAppointmentsForClientShouldReturnTrue() {
    var client = this.clientRepository.findById(3l)
        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));

    var appointment = this.appointmentRepository.existsOpenOrPresentAppointmentsForClient(client,
        LocalDate.parse("2028-04-03"), LocalTime.parse("08:00:00"), LocalTime.parse("08:30:00"));

    assertTrue(appointment);
  }

  @Test
  void existsOpenOrPresentAppointmentsForClientShouldReturnFalse() {
    var client = this.clientRepository.findById(3l)
        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));

    var appointment = this.appointmentRepository.existsOpenOrPresentAppointmentsForClient(client,
        LocalDate.parse("2038-04-03"), LocalTime.parse("08:00:00"), LocalTime.parse("08:30:00"));

    assertFalse(appointment);
  }
}

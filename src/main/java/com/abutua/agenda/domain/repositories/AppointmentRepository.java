package com.abutua.agenda.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}

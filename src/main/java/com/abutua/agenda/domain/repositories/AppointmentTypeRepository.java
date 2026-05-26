package com.abutua.agenda.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.AppointmentType;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Integer> {

}

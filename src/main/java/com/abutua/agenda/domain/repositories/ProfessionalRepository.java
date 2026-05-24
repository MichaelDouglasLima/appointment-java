package com.abutua.agenda.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}

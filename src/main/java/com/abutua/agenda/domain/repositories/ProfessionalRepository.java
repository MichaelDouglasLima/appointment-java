package com.abutua.agenda.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abutua.agenda.domain.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
  boolean existsByIdAndAreas_Id(Long professionalId, Integer areaId);

  @Query("SELECT COUNT(p) > 0 " +
      "FROM Professional p JOIN p.areas a " +
      "WHERE p.id = :professionalId AND a.id = :areaId")
  boolean existsAssocioationWithArea(Long professionalId, Integer areaId);

  Page<Professional> findByNameContainingIgnoreCase(String name, Pageable page);
}

package com.abutua.agenda.unit.domain.repositories;

import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@EnabledIf(expression = "#{environment.acceptsProfiles('test')}", loadContext = true)
@SpringBootTest
public class ProfessionalRepositoryTest {

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Test
  public void existsAssocioationWithAreaShouldReturnTrue() {
    long professionalId = 6;
    int areaId = 1;

    boolean association = this.professionalRepository.existsAssociationWithArea(professionalId, areaId);

    assertTrue(association);
  }

  @Test
  public void findByNameContainingIgnoreCaseShouldFindMarcelo() {
    String expProfessionalName = "Marcelo";
    Pageable pageable = Pageable.ofSize(10);

    Page<Professional> page = this.professionalRepository
        .findByNameContainingIgnoreCase(expProfessionalName, pageable);

    assertTrue(page.hasContent(), "Nenhum profissional encontrado!");

    Professional p = page.getContent().get(0);

    assertTrue(p.getName().toLowerCase().contains(expProfessionalName.toLowerCase()));
  }
}

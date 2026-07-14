package com.abutua.agenda.unit.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.abutua.agenda.domain.entities.Professional;

public class ProfessionalTest {

  @Test
  public void getActiveShouldReturnTrue() {
    Professional p = new Professional();

    p.setActive(true);

    assertTrue(p.isActive());
  }

  @Test
  public void getActiveShouldReturnFalse() {
    Professional p = new Professional();

    p.setActive(false);

    assertFalse(p.isActive());
  }

  @Test
  public void constructorShouldSetAllAttributes() {
    String expName = "Pedro";
    String expPhone = "15 999999999";
    boolean expActive = true;

    Professional p = new Professional(expName, expPhone, expActive);

    assertEquals(expName, p.getName());
    assertEquals(expPhone, p.getPhone());
    assertEquals(expActive, p.isActive());
  }
}

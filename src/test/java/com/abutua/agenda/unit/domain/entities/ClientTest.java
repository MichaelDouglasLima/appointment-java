package com.abutua.agenda.unit.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.abutua.agenda.domain.entities.Client;

public class ClientTest {

  @Test
  void getDateOfBirthShouldReturnNull() {
    Client c = new Client();
    assertNull(c.getDateOfBirth());
  }

  @Test
  void getDateOfBirthShouldReturnLocalDate() {
    Client c = new Client();
    LocalDate expLocalDate = LocalDate.parse("2024-08-01");

    c.setDateOfBirth(expLocalDate);

    assertEquals(expLocalDate, c.getDateOfBirth());
  }

  @Test
  void constructorShouldSetAllAttributes() {
    LocalDate expLocalDate = LocalDate.parse("2024-08-01");
    String expName = "Ana";
    String expPhone = "15 999999999";
    String expComments = "none";

    Client c = new Client(expName, expPhone, expLocalDate, expComments);

    assertEquals(expName, c.getName());
    assertEquals(expPhone, c.getPhone());
    assertEquals(expLocalDate, c.getDateOfBirth());
    assertEquals(expComments, c.getComments());
    assertNull(c.getId());
  }
}

package com.abutua.agenda.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CLIENT")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Client extends Person {
  private LocalDate dateOfBirth;

  Client() {

  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

}

package com.abutua.agenda.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_USER")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class User extends Person {
  private String email;
  private String password;

  User() {

  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

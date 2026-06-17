package com.abutua.agenda.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CLIENT")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Client extends Person {
  private LocalDate dateOfBirth;

  @Column(length = 1024)
  private String comments;

  @OneToMany(mappedBy = "client")
  private List<Appointment> appointments = new ArrayList<>();

  public Client() {

  }

  public Client(String name, String phone, LocalDate dateOfBirh) {
    super(name, phone);
    this.dateOfBirth = dateOfBirh;
  }

  public Client(String name, String phone, LocalDate dateOfBirh, String comments) {
    super(name, phone);
    this.dateOfBirth = dateOfBirh;
    this.comments = comments;
  }

  public Client(Long id) {
    super(id);
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  // public ClientResponse toDTO() {
  // ClientResponse clientResponse = new ClientResponse();

  // clientResponse.setId(this.getId());
  // clientResponse.setName(this.getName());
  // clientResponse.setPhone(this.getPhone());
  // clientResponse.setDateOfBirth(this.dateOfBirth);

  // return clientResponse;
  // }

  @Override
  public String toString() {
    return "Client [dateOfBirth=" + dateOfBirth + " " + super.toString() + "]";
  }

}

package com.abutua.agenda.domain.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_APPOINTMENT")
public class Appointment implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date date;

  private LocalTime startTime;

  private LocalTime endTime;

  @Column(length = 1024)
  private String comments;

  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  private AppointmentStatus status = AppointmentStatus.OPEN;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "PROFESSIONAL_ID")
  private Professional professional;

  @ManyToOne
  @JoinColumn(name = "AREA_ID")
  private Area area;

  @ManyToOne
  @JoinColumn(name = "APPOINTMENT_TYPE_ID")
  private AppointmentType appointmentType;

  public Appointment() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Professional getProfessional() {
    return professional;
  }

  public void setProfessional(Professional professional) {
    this.professional = professional;
  }

  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public AppointmentType getAppointmentType() {
    return appointmentType;
  }

  public void setAppointmentType(AppointmentType appointmentType) {
    this.appointmentType = appointmentType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Appointment other = (Appointment) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}

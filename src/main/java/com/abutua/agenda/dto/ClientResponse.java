package com.abutua.agenda.dto;

import java.time.LocalDate;

public record ClientResponse(
    Long id, String name, String phone, LocalDate dateOfBirth, String comments) {
}

// public class ClientResponse {
// private Long id;
// private String name;
// private String phone;
// private LocalDate dateOfBirth;

// public ClientResponse() {

// }

// public Long getId() {
// return id;
// }

// public void setId(Long id) {
// this.id = id;
// }

// public String getName() {
// return name;
// }

// public void setName(String name) {
// this.name = name;
// }

// public String getPhone() {
// return phone;
// }

// public void setPhone(String phone) {
// this.phone = phone;
// }

// public LocalDate getDateOfBirth() {
// return dateOfBirth;
// }

// public void setDateOfBirth(LocalDate dateOfBirth) {
// this.dateOfBirth = dateOfBirth;
// }

// }

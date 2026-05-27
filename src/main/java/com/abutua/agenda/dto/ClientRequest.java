package com.abutua.agenda.dto;

import java.time.LocalDate;

public record ClientRequest(String name, String phone, LocalDate dateOfBirth) {

}

package com.abutua.agenda.web.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.domain.services.AppointmentService;
import com.abutua.agenda.dto.AppointmentRequest;
import com.abutua.agenda.dto.AppointmentResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

  @Autowired
  private AppointmentService appointmentService;

  @PostMapping
  public ResponseEntity<AppointmentResponse> createAppointment(
      @Validated @RequestBody AppointmentRequest appointmentRequest) {
    // AppointmentResponse appointmentResponse =
    // this.clientService.save(appointmentRequest);

    var appointmentResponse = this.appointmentService.createAppointment(appointmentRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(appointmentResponse.id())
        .toUri();

    return ResponseEntity.created(location).body(appointmentResponse);
  }

}

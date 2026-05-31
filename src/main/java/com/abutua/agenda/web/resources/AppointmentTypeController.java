package com.abutua.agenda.web.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.services.AppointmentTypeService;
import com.abutua.agenda.dto.AppointmentTypeResponse;

@RestController
@RequestMapping("appointment-types")
public class AppointmentTypeController {

  @Autowired
  private AppointmentTypeService appointmentTypeService;

  @GetMapping
  public ResponseEntity<List<AppointmentTypeResponse>> getAppointmentTypes() {
    List<AppointmentTypeResponse> appointmentTypesResponse = this.appointmentTypeService.getAppointmentTypes();
    return ResponseEntity.ok(appointmentTypesResponse);
  }

}

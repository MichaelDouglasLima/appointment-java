package com.abutua.agenda.web.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.models.TimeSlot;
import com.abutua.agenda.domain.services.ProfessionalService;
import com.abutua.agenda.dto.TimeSlotResponse;

@RestController
@RequestMapping("professionals")
public class ProfessionalController {

  @Autowired
  private ProfessionalService professionalService;

  @GetMapping("{id}/availability-times")
  public ResponseEntity<List<TimeSlotResponse>> getAvailabilityTimes(@PathVariable long id,
      @RequestParam(name = "date", defaultValue = "") String dateStr) {

    LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
    return ResponseEntity.ok(this.professionalService.getAvailabilityTimes(id, date));
  }
}

package com.abutua.agenda.web.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.services.ProfessionalService;
import com.abutua.agenda.dto.TimeSlotResponse;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("professionals")
@Validated
public class ProfessionalController {

  @Autowired
  private ProfessionalService professionalService;

  @GetMapping("{id}/availability-times")
  public ResponseEntity<List<TimeSlotResponse>> getAvailabilityTimes(@PathVariable long id,
      @RequestParam(name = "date", required = false) @NotNull(message = "O parâmetro data é requirido!") @FutureOrPresent(message = "A data ver ser igual ou maior que a data atual!") LocalDate date) {
    return ResponseEntity.ok(this.professionalService.getAvailabilityTimesFromProfessional(id, date));
  }

  @GetMapping("{id}/availability-days")
  public ResponseEntity<List<Integer>> getAvailabilityDays(@PathVariable long id,
      @NotNull(message = "O parâmetro mês é requirido!") @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "Formato do mês inválido! Utilize um valor de 1 à 12.") @RequestParam(name = "month", required = false) String month,

      @NotNull(message = "O parâmetro ano é requirido!") @Min(value = 1900, message = "O ano deve ser maior que 1900") @Pattern(regexp = "^\\d{4}$", message = "Formato do ano inválido! Utilize o formato: 'yyyy'.") @RequestParam(name = "year", required = false) String year) {

    List<Integer> days = this.professionalService.getAvailabilityDaysFromProfessional(id, Integer.valueOf(month),
        Integer.valueOf(year));

    return ResponseEntity.ok(days);
  }
}

package com.abutua.agenda.domain.services.usecases.read;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.services.exceptions.ParameterException;

@Service
public class SearchProfessionalAvailabiltyDaysUseCase {

  public List<Integer> executeUseCase(Professional professional, int month, int year) {
    checkMonthIsValidOrThrowsException(113);
    checkYearIsValidOrThrowsException(year);
    checkMonthAndYearIsValidOrThrowsException(month, year);
    return null;
  }

  private void checkMonthAndYearIsValidOrThrowsException(int month, int year) {
  }

  private void checkYearIsValidOrThrowsException(int year) {
  }

  private void checkMonthIsValidOrThrowsException(int month) {
    if (month < 1 || month > 12) {
      throw new ParameterException("Mês inválido. O valor do mês deve ser de 1 a 12.");
    }
  }
}

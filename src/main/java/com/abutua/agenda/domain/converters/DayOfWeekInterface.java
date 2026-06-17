package com.abutua.agenda.domain.converters;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;

public interface DayOfWeekInterface extends AttributeConverter<DayOfWeek, Integer> {

}
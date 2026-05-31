package com.abutua.agenda.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.AppointmentType;
import com.abutua.agenda.domain.mappers.AppointmentTypeMapper;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.dto.AppointmentTypeResponse;

@Service
public class AppointmentTypeService {

  @Autowired
  private AppointmentTypeRepository appointmentTypeRepository;

  @Transactional(readOnly = true)
  public List<AppointmentTypeResponse> getAppointmentTypes() {
    List<AppointmentType> appointmentTypes = this.appointmentTypeRepository.findAll();
    return appointmentTypes.stream().map(apt -> AppointmentTypeMapper.toAppointmentTypeResponseDTO(apt))
        .collect(Collectors.toList());
  }
}

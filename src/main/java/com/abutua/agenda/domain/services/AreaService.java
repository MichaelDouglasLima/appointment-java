package com.abutua.agenda.domain.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abutua.agenda.domain.entities.Area;
import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.mappers.AreaMapper;
import com.abutua.agenda.domain.mappers.ProfessionalMapper;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.ProfessionalResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {

  @Autowired
  private AreaRepository areaRepository;

  @Transactional(readOnly = true)
  public List<AreaResponse> getAreas() {
    List<Area> areas = this.areaRepository.findAll();
    return areas.stream().map(a -> AreaMapper.toAreaResponseDTO(a)).collect((Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public Set<ProfessionalResponse> getProfessionalsByAreaId(int areaId, Boolean active) {
    Area area = this.areaRepository.findById(areaId)
        .orElseThrow(() -> new EntityNotFoundException("Área não encontrada!"));

    Stream<Professional> stream = area.getProfessionals().stream();

    if (active) {
      stream = stream.filter(p -> p.isActive());
    }

    return stream.map(ProfessionalMapper::toProfessionalResponseDTO)
        .collect(Collectors.toSet());
  }
}

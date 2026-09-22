package com.abutua.agenda.unit.domain.services;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abutua.agenda.domain.entities.Professional;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.services.ProfessionalService;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProfessionalServiceTest {

  @Mock
  private ProfessionalRepository professionalRepository;

  @InjectMocks
  private ProfessionalService professionalService;

  @Test
  public void saveShouldPersistProfessional() {
    String name = "Pedro";
    String phone = "159999999";
    boolean active = true;

    Professional newProfessional = new Professional(name, phone, active);
    Professional savedProfessional = new Professional(1l, name, phone, active);

    given(professionalRepository.save(newProfessional)).willReturn(savedProfessional);

    ProfessionalRequest newProfessionalRequest = new ProfessionalRequest(name, phone, active, List.of());

    ProfessionalResponse exProfessionalRequest = new ProfessionalResponse(1l, name, phone, active);

    var foundProfessionalResponse = professionalService.save(newProfessionalRequest);

    verify(professionalRepository).save(any(Professional.class));

    assertNotNull(foundProfessionalResponse);
    assertNotNull(foundProfessionalResponse.id());

    assertEquals(exProfessionalRequest.id(), foundProfessionalResponse.id());
    assertEquals(exProfessionalRequest.name(), foundProfessionalResponse.name());
    assertEquals(exProfessionalRequest.phone(), foundProfessionalResponse.phone());
    assertEquals(exProfessionalRequest.active(), foundProfessionalResponse.active());
  }

  @Test
  public void updateShouldPersistProfessional() {
    Professional professional = mock(Professional.class);

    given(professionalRepository.getReferenceById(anyLong())).willReturn(professional);
    given(professionalRepository.save(professional)).willReturn(professional);

    ProfessionalRequest professionalRequest = new ProfessionalRequest("Pedro", "159999999", true, List.of());

    professionalService.updateById(anyLong(), professionalRequest);

    verify(professional).setName(anyString());
    verify(professional).setPhone(anyString());
    verify(professional).setActive(anyBoolean());

    verify(professionalRepository).save(any(Professional.class));
    verify(professionalRepository).getReferenceById(anyLong());
  }

  @Test
  public void updateShouldThrowsEntityNotFoundException() {
    given(professionalRepository.getReferenceById(anyLong())).willThrow(EntityNotFoundException.class);
    ProfessionalRequest professionalRequest = mock(ProfessionalRequest.class);
    assertThrows(EntityNotFoundException.class, () -> professionalService.updateById(anyLong(), professionalRequest));
    verify(professionalRepository).getReferenceById((any(Long.class)));
  }
}

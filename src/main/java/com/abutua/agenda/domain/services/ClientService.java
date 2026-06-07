package com.abutua.agenda.domain.services;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.mappers.ClientMapper;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.services.exceptions.DatabaseException;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Transactional(readOnly = true)
  public Page<ClientResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Client> pageClient = this.clientRepository.findByNameContainingIgnoreCase(name, pageRequest);
    return pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
  }

  @Transactional(readOnly = true)
  public ClientResponse getById(long id) {
    // Client client = this.clientRepository.findById(id)
    // .orElseThrow(() -> new EntityNotFoundException("Client not found!"));

    var client = this.clientRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));

    return ClientMapper.toClientResponseDTO(client);
  }

  @Transactional
  public ClientResponse save(ClientRequest clientRequest) {
    var client = this.clientRepository.save(ClientMapper.fromClientRequestDTO(clientRequest));
    return ClientMapper.toClientResponseDTO(client);
  }

  @Transactional
  public void updateById(long id, ClientRequest clientUpdate) {
    try {
      Client client = this.clientRepository.getReferenceById(id);

      client.setName(clientUpdate.name());
      client.setPhone(clientUpdate.phone());
      client.setDateOfBirth(clientUpdate.dateOfBirth());
      client.setComments(clientUpdate.comments());

      this.clientRepository.save(client);
    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Client not found!");
    }
  }

  @Transactional
  public void deleteById(long id) {
    try {
      if (this.clientRepository.existsById(id)) {
        this.clientRepository.deleteById(id);
      } else {
        throw new EntityNotFoundException("Client not found!");
      }

    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Constraint violation, client cant't delete");
    }
  }

}

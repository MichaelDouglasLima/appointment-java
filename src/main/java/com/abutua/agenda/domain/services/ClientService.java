package com.abutua.agenda.domain.services;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.domain.mappers.ClientMapper;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.dto.ClientResponse;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Page<ClientResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Client> pageClient = this.clientRepository.findByNameContainingIgnoreCase(name, pageRequest);
    return pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
  }

  public ClientResponse getById(long id) {
    Client client = this.clientRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Client not found!"));
    return ClientMapper.toClientResponseDTO(client);
  }
}

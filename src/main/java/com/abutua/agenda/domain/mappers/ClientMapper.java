package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.dto.ClientResponse;

public class ClientMapper {

  public static ClientResponse toClientResponseDTO(Client client) {
    ClientResponse clientResponse = new ClientResponse();

    clientResponse.setId(client.getId());
    clientResponse.setName(client.getName());
    clientResponse.setPhone(client.getPhone());
    clientResponse.setDateOfBirth(client.getDateOfBirth());

    return clientResponse;
  }
}

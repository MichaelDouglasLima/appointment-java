package com.abutua.agenda.domain.mappers;

import com.abutua.agenda.domain.entities.Client;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

public class ClientMapper {

  public static ClientResponse toClientResponseDTO(Client client) {
    ClientResponse clientResponse = new ClientResponse(client.getId(), client.getName(), client.getPhone(),
        client.getDateOfBirth());

    return clientResponse;
  }

  public static Client fromClientRequestDTO(ClientRequest clientRequest) {
    return new Client(
        clientRequest.name(),
        clientRequest.phone(),
        clientRequest.dateOfBirth());
  }

  // public static Client toClientEntity(ClientRequest clientRequest) {
  // Client client = new Client();

  // client.setName(clientRequest.name());
  // client.setPhone(clientRequest.phone());
  // client.setDateOfBirth(clientRequest.dateOfBirth());

  // return client;
  // }
}

// public class ClientMapper {

// public static ClientResponse toClientResponseDTO(Client client) {
// ClientResponse clientResponse = new ClientResponse();

// clientResponse.setId(client.getId());
// clientResponse.setName(client.getName());
// clientResponse.setPhone(client.getPhone());
// clientResponse.setDateOfBirth(client.getDateOfBirth());

// return clientResponse;
// }
// }

package com.abutua.agenda.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.services.ClientService;
import com.abutua.agenda.dto.ClientResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @GetMapping
  public ResponseEntity<Page<ClientResponse>> getClients(
      @RequestParam(name = "name_like", defaultValue = "") String name,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok(this.clientService.findByNameContainingIgnoreCase(name, page, size));
  }

  @GetMapping("{id}")
  public ResponseEntity<ClientResponse> getClient(@PathVariable long id) {
    ClientResponse clientResponse = this.clientService.getById(id);
    return ResponseEntity.ok(clientResponse);
  }
}

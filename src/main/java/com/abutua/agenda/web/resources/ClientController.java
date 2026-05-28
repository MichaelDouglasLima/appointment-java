package com.abutua.agenda.web.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abutua.agenda.domain.services.ClientService;
import com.abutua.agenda.dto.ClientRequest;
import com.abutua.agenda.dto.ClientResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping
  public ResponseEntity<ClientResponse> save(@Validated @RequestBody ClientRequest clientRequest) {
    ClientResponse clientResponse = this.clientService.save(clientRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clientResponse.id())
        .toUri();

    return ResponseEntity.created(location).body(clientResponse);
  }

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

  @PutMapping("{id}")
  public ResponseEntity<Void> updateClient(@PathVariable long id, @RequestBody ClientRequest clientUpdate) {
    this.clientService.updateById(id, clientUpdate);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable long id) {
    this.clientService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

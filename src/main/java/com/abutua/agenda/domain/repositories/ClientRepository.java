package com.abutua.agenda.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abutua.agenda.domain.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}

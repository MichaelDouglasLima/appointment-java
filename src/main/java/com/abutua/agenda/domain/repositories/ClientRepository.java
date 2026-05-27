package com.abutua.agenda.domain.repositories;

// import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.abutua.agenda.domain.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

  // @Query(value = "SELECT * FROM TBL_CLIENT AS C JOIN TBL_PERSON AS P ON
  // C.PERSON_ID = P.ID", nativeQuery = true)
  // public List<Client> myFindAll();

  // @Query(value = "SELECT * FROM TBL_CLIENT AS C JOIN TBL_PERSON AS P ON
  // c.person_id = p.id WHERE UPPER (P.name) LIKE CONCAT('%',UPPER(:name),'%')",
  // nativeQuery = true)
  // public List<Client> myFindAll(@Param("name") String name);

  Page<Client> findByNameContainingIgnoreCase(String name, Pageable page);
}

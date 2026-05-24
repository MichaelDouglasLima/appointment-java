package com.abutua.agenda.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROFESSIONAL")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Professional extends Person {
  private Boolean active;

  Professional() {

  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

}

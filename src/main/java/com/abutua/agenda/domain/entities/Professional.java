package com.abutua.agenda.domain.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROFESSIONAL")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
public class Professional extends Person {
  private boolean active;

  @ManyToMany
  @JoinTable(name = "TBL_AREA_PROFESSIONAL", joinColumns = @JoinColumn(name = "professional_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
  private Set<Area> areas = new HashSet<>();

  Professional() {

  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Set<Area> getAreas() {
    return areas;
  }

  public void setAreas(Set<Area> areas) {
    this.areas = areas;
  }

}

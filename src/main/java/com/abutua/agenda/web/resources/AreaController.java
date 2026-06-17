package com.abutua.agenda.web.resources;

import org.springframework.web.bind.annotation.RestController;

import com.abutua.agenda.domain.services.AreaService;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.ProfessionalResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("areas")
public class AreaController {

  @Autowired
  private AreaService areaService;

  @GetMapping
  public ResponseEntity<List<AreaResponse>> getAreas() {
    List<AreaResponse> areasResponse = this.areaService.getAreas();
    return ResponseEntity.ok(areasResponse);
  }

  @GetMapping("{id}/professionals")
  public ResponseEntity<List<ProfessionalResponse>> getProfessionalsByArea(@PathVariable int id) {
    return ResponseEntity.ok(areaService.getProfessionalsByArea(id));
  }

  // @GetMapping("{id}/professionals")
  // public ResponseEntity<Set<ProfessionalResponse>>
  // getProfessionalsByAreaId(@PathVariable int id,
  // @RequestParam(name = "active", required = false) boolean active) {
  // Set<ProfessionalResponse> professionalsResponse =
  // this.areaService.getProfessionalsByArea(id, active);
  // return ResponseEntity.ok(professionalsResponse);
  // }
}

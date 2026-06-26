package com.abutua.agenda.unit.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

// import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.abutua.agenda.domain.repositories.AreaRepository;

// @DataJpaTest
@EnabledIf(expression = "#{environment.acceptsProfiles('test')}", loadContext = true)
@SpringBootTest
public class AreaRepositoryTest {

  @Autowired
  private AreaRepository areaRepository;

  @Test
  void findActiveProfessionalsByIdShouldReturnProfessionals() {
    var expSizeArea1 = 2;
    var expSizeArea2 = 1;
    var expSizeArea3 = 1;

    var professionals = areaRepository.findActiveProfessionalsById(1);
    assertEquals(expSizeArea1, professionals.size());

    professionals = areaRepository.findActiveProfessionalsById(2);
    assertEquals(expSizeArea2, professionals.size());

    professionals = areaRepository.findActiveProfessionalsById(3);
    assertEquals(expSizeArea3, professionals.size());

  }

  @Test
  void findActiveProfessionalsByIdShouldNotReturnProfessionals() {
    var expSizeArea4 = 0;

    var professionals = areaRepository.findActiveProfessionalsById(4);
    assertEquals(expSizeArea4, professionals.size());
  }

  // @Test
  // void findActiveProfessionalsByIdShouldReturnProfessionals() {

  // Integer area1Id = 1;
  // Integer area2Id = 2;
  // Integer area3Id = 3;

  // Area area1 = this.areaRepository.findById(area1Id)
  // .orElseThrow(() -> new EntityNotFoundException("Área não encontrada! Id = " +
  // area1Id));

  // Area area2 = this.areaRepository.findById(area2Id)
  // .orElseThrow(() -> new EntityNotFoundException("Área não encontrada! Id = " +
  // area2Id));

  // Area area3 = this.areaRepository.findById(area3Id)
  // .orElseThrow(() -> new EntityNotFoundException("Área 3 não encontrada! Id = "
  // + area3Id));

  // List<Professional> professionalsArea1 =
  // this.areaRepository.findActiveProfessionalsById(area1.getId());
  // List<Professional> professionalsArea2 =
  // this.areaRepository.findActiveProfessionalsById(area2.getId());
  // List<Professional> professionalsArea3 =
  // this.areaRepository.findActiveProfessionalsById(area3.getId());

  // assertEquals(professionalsArea1.stream().toArray().length, 2);
  // assertEquals(professionalsArea2.stream().toArray().length, 1);
  // assertEquals(professionalsArea3.stream().toArray().length, 1);
  // }
}

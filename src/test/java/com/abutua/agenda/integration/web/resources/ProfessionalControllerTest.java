package com.abutua.agenda.integration.web.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.abutua.agenda.dto.ProfessionalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessionalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getAvailabilityTimes_WithAppointments_Ok() throws Exception {
    // Professinals works in this WeekDay (MONDAY) and have appointments
    // (See data.sql)
    LocalDate dateFuture = LocalDate.parse("2028-04-03");
    var result = mockMvc.perform(
        get("/professionals/6/availability-times").param("date", dateFuture.toString()));

    String times[] = { "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00" };
    Boolean available[] = { false, false, false, false };
    for (int i = 0; i < 4; i++) {
      result.andExpect(jsonPath("$[" + i + "].startTime").value(times[i]))
          .andExpect(jsonPath("$[" + i + "].endTime").value(times[i + 1]))
          .andExpect(jsonPath("$[" + i + "].available").value(available[i]));
    }

    dateFuture = LocalDate.parse("2028-04-10");
    result = mockMvc.perform(
        get("/professionals/6/availability-times").param("date", dateFuture.toString()));

    available = Arrays.array(true, true, true, false);
    for (int i = 0; i < 4; i++) {
      result.andExpect(jsonPath("$[" + i + "].startTime").value(times[i]))
          .andExpect(jsonPath("$[" + i + "].endTime").value(times[i + 1]))
          .andExpect(jsonPath("$[" + i + "].available").value(available[i]));
    }

  }

  @Test
  public void getAvailabilityTimes_WithoutAppointments_Ok() throws Exception {

    LocalDate dateFuture = LocalDate.now().plusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));

    // Professinals works in this WeekDay TUESDAY and does not have appointments in
    // data.sql
    var result = mockMvc.perform(
        get("/professionals/6/availability-times").param("date", dateFuture.toString()));

    String times1[] = { "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00",
        "12:00:00" };

    for (int i = 0; i < 8; i++) {
      result.andExpect(jsonPath("$[" + i + "].startTime").value(times1[i]))
          .andExpect(jsonPath("$[" + i + "].endTime").value(times1[i + 1]))
          .andExpect(jsonPath("$[" + i + "].available").value(true));
    }

    String times2[] = { "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00",
        "18:00:00" };
    for (int i = 0; i < 8; i++) {
      result.andExpect(jsonPath("$[" + (i + 8) + "].startTime").value(times2[i]))
          .andExpect(jsonPath("$[" + (i + 8) + "].endTime").value(times2[i + 1]))
          .andExpect(jsonPath("$[" + (i + 8) + "].available").value(true));
    }

  }

  @Test
  public void getAvailabilityTimes_EmptyList_Ok() throws Exception {
    // Professinals does not work in this WeekDay (Wednesday)
    var dateFuture = LocalDate.now().plusWeeks(100).with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
    var result = mockMvc.perform(
        get("/professionals/6/availability-times").param("date", dateFuture.toString()));
    result.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  public void getProfessionalById_Ok() throws Exception {
    var result = mockMvc.perform(get("/professionals/6"));

    result.andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(6)))
        .andExpect(jsonPath("$.name", equalTo("Marcelo Silva")))
        .andExpect(jsonPath("$.phone", equalTo("13 999216212")))
        .andExpect(jsonPath("$.active", equalTo(true)));
  }

  @Test
  public void getProfessionalById_NotFound() throws Exception {
    var result = mockMvc.perform(get("/professionals/404"));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void saveProfessional_Created() throws Exception {
    ProfessionalRequest professionalRequest = new ProfessionalRequest("Pedro Rocha", "15 999336117", true,
        List.of());

    var result = mockMvc.perform(post("/professionals")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(professionalRequest)));

    result.andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Pedro Rocha"))
        .andExpect(jsonPath("$.phone").value("15 999336117"))
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  public void saveProfessional_UnprocessableEntity() throws Exception {
    ProfessionalRequest professionalRequest = new ProfessionalRequest("", "", null,
        List.of());

    var result = mockMvc.perform(post("/professionals")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(professionalRequest)));

    result.andExpect(status().isUnprocessableContent())
        .andExpect(jsonPath("$.errors", hasSize(3)));
  }

  @Test
  public void deleteProfessional_NoContent() throws Exception {
    var result = mockMvc.perform(delete("/professionals/8"));

    result.andExpect(status().isNoContent());
  }

  @Test
  public void deleteProfessional_NotFound() throws Exception {
    var result = mockMvc.perform(delete("/professionals/404"));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void deleteProfessionalWithAppointments_BadRequest() throws Exception {
    var result = mockMvc.perform(delete("/professionals/6"));

    result.andExpect(status().isBadRequest());
  }
}

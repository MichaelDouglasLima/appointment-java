package com.abutua.agenda.integration.web.resources;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.abutua.agenda.dto.ClientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void saveClient_Created() throws Exception {
    ClientRequest clientRequest = new ClientRequest("Fernanda Gomes",
        "11 92213212",
        LocalDate.parse("1978-10-21"),
        "No comments");

    var result = mockMvc.perform(post("/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result.andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Fernanda Gomes"))
        .andExpect(jsonPath("$.phone").value("11 92213212"))
        .andExpect(jsonPath("$.dateOfBirth").value("1978-10-21"))
        .andExpect(jsonPath("$.comments").value("No comments"));
  }

  @Test
  public void saveClient_UnprocessableEntity() throws Exception {
    ClientRequest clientRequest = new ClientRequest("",
        "",
        null,
        "No comments");

    var result = mockMvc.perform(post("/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result.andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.errors", hasSize(3)));
  }

  @Test
  public void GetPostGet_Success() throws Exception {
    // GET
    var result = mockMvc.perform(get("/clients"));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(3)))
        .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(3, 4, 5)))
        .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("Ana Maria", "Pedro Silva", "Marco Nunes")))
        .andExpect(jsonPath("$.content[*].phone",
            containsInAnyOrder("15 992231122", "15 923233212", "11 902324322")))
        .andExpect(jsonPath("$.content[*].dateOfBirth",
            containsInAnyOrder("2000-08-02", "1998-01-22", "1998-01-22")))
        .andExpect(
            jsonPath("$.content[*].comments", containsInAnyOrder("Parcelar em até 4x", "Indicação", "")));

    // POST
    ClientRequest clientRequest = new ClientRequest("Fernanda Gomes",
        "11 92213212",
        LocalDate.parse("1978-10-21"),
        "No comments");

    result = mockMvc.perform(post("/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result.andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Fernanda Gomes"))
        .andExpect(jsonPath("$.phone").value("11 92213212"))
        .andExpect(jsonPath("$.dateOfBirth").value("1978-10-21"))
        .andExpect(jsonPath("$.comments").value("No comments"));

    // GET
    result = mockMvc.perform(get("/clients"));

    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(4)))
        .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(3, 4, 5, 9)))
        .andExpect(jsonPath("$.content[*].name",
            containsInAnyOrder("Ana Maria", "Pedro Silva", "Marco Nunes", "Fernanda Gomes")))
        .andExpect(jsonPath("$.content[*].phone",
            containsInAnyOrder("15 992231122", "15 923233212", "11 902324322", "11 92213212")))
        .andExpect(jsonPath("$.content[*].dateOfBirth",
            containsInAnyOrder("2000-08-02", "1998-01-22", "1998-01-22", "1978-10-21")))
        .andExpect(jsonPath("$.content[*].comments",
            containsInAnyOrder("Parcelar em até 4x", "Indicação", "", "No comments")));

  }
}

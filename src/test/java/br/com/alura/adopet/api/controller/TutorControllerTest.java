package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @MockBean
    private TutorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sucessoRequisicaoDeCadastrarTutor() throws Exception {
        // Arrange
        String json = """
                {
                    "nome": "Fulano",
                    "telefone": "(11)9876-5432",
                    "email": "fulano@email.com.br"
                }
                """;
        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoCadastrarTutorDadosInvalidos() throws Exception {
        // Arrange
        String json = """
                {
                    "nome": "Fulano",
                    "telefone": "(11)9876-54320",
                    "email":"fulano@email.com.br"
                }
                """;

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void sucessoRequisicaoAtualizarTutor() throws Exception {
        // Arrange
        String json = """
                {
                    "id" : "1",
                    "nome": "Fulano",
                    "telefone": "(11)9876-5432",
                    "email": "email@example.com"
                }
                """;

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoAtualizarTutor() throws Exception {
        // Arrange
        String json = """
                {
                    "id" : "2",
                    "nome": "Fulano",
                    "telefone": "(11)9876-54320",
                    "email":"fulano@email.com.br"
                }
                """;

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(400, response.getStatus());
    }
}
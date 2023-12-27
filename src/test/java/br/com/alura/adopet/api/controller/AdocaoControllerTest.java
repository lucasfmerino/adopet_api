package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.adopet.api.service.AdocaoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Test
    void erroSolicitacaoAdocao() throws Exception {
        // ARRANGE
        String json = "{}";

        // ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void sucessoSolicitacaoAdocao() throws Exception {
        // ARRANGE
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo teste"
                }
                """;

        // ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    
    @Test
    void sucessoRequisicaoAprovarAdocao() throws Exception {
        // ARRANGE
        String json = """
                {
                    "idAdocao": 1
                }
                """;

        // ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoAprovarAdocaoInvalida() throws Exception {
        // ARRANGE
        String json = """
                {

                }
                """;

        // ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void sucessoRequisicaoReprovarAdocao() throws Exception {
        // ARRANGE
        String json = """
                {
                    "idAdocao": 1,
                    "justificativa": "teste"
                }
                """;

        // ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoReprovarAdocaoInvalido() throws Exception {
        // ARRANGE
        String json = """
                {

                }
                """;

        // ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

}

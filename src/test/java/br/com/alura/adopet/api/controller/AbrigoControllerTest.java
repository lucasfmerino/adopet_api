package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sucessoRequisicaoListarAbrigos() throws Exception {
        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos")).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void sucessoRequisicaoCadastrarAbrigo() throws Exception {
        // ARRANGE
        String json = """
                {
                    "nome": "Wonka Pets",
                    "telefone": "(11)9876-5432",
                    "email": "wonkapets@example.com.br"
                }
                """;

        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoCadastrarAbrigo() throws Exception {
        // ARRANGE
        String json = """
                {
                    "nome": "Wonka Pets",
                    "telefone": "(11)9876-54321",
                    "email": "email@example.com.br"
                }
                """;

        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void sucessoRequisicaoListarPetsDoAbrigoPorNome() throws Exception {
        // Arrange
        String nome = "Wonka Pets";

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void sucessoRequisicaoListarPetsDoAbrigoPorId() throws Exception {
        // Arrange
        String id = "1";

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoListarPetsDoAbrigoPorIdInvalido() throws Exception {
        // Arrange
        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)).andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
    }

    @Test
    void erroRequisicaoListarPetsDoAbrigoPorNomeInvalido() throws Exception {
        // Arrange
        String nome = "Hyrax";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)).andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
    }

    @Test
    void sucessoRequisicaoCadastrarPetPeloId() throws Exception {
        // Arange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Hyrax",
                    "raca": "siames",
                    "idade": "5",
                    "cor" : "Cinza",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void sucessoRequisicaoCadastrarPetPeloNome() throws Exception {
        // Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Hyrax",
                    "raca": "siames",
                    "idade": "5",
                    "cor" : "Cinza",
                    "peso": "6.4"
                }
                """;

        String abrigoNome = "Wonka Pets";

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void erroRequisicaoDeCadastrarPetAbrigoNaoEncontradoPeloId() throws Exception {
        // Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Hyrax",
                    "raca": "siames",
                    "idade": "5",
                    "cor" : "Cinza",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        given(abrigoService.carregarAbrigo(abrigoId)).willThrow(ValidacaoException.class);

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
    }

    @Test
    void erroRequisicaoCadastrarPetAbrigoNaoEncontradoPeloNome() throws Exception {
        // Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Hyrax",
                    "raca": "siames",
                    "idade": "5",
                    "cor" : "Cinza",
                    "peso": "6.4"
                }
                """;

        String abrigoNome = "Wonka Pets";

        given(abrigoService.carregarAbrigo(abrigoNome)).willThrow(ValidacaoException.class);

        // Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
    }

}
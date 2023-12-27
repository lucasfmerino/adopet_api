package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;

public class CalculadoraProbabilidadeAdocaoTest {

    @Test
    void ProbabilidadeAltaParaPesoBaixaIdadeBaixa() {
        // Gato idade 4 anos, peso 4kg - ALTA

        // Arrange
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Wonka Pets", "11987654321", "wonkapets@email.com"));

        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Hyrax", "Siames", 4, "Cinza", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        // Act
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        // Assert
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    void ProbabilidadeMediaParaPesoBaixoIdadeAlta() {
        // Gato idade 15 anos, peso 4kg - ALTA

        // Arrangeø
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Wonka Pets", "11987654321", "wonkapets@email.com"));

        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Hyrax", "Siames", 15, "Cinza", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        // Act
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        // Assert
        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }
}

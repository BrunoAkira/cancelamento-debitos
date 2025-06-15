package br.com.cancelamento.application;

import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import br.com.cancelamento.domain.ports.output.TransacaoRepository;
import br.com.cancelamento.exceptions.CancelamentoForaDoPrazoException;
import br.com.cancelamento.exceptions.TransacaoNaoEncontradaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoServiceTest {

    @Test
    void devePermitirCancelamentoDentroDoPrazo() {
        // Arrange
        PublicadorEvento publicador = mock(PublicadorEvento.class);
        TransacaoRepository repo = mock(TransacaoRepository.class);
        when(repo.buscarDataTransacaoPorId("abc123")).thenReturn(LocalDateTime.now().minusDays(3));

        CancelamentoService service = new CancelamentoService(publicador, repo);

        // Act
        service.cancelar("abc123", "Cliente solicitou cancelamento");

        // Assert
        verify(publicador, times(1)).publicarEventoCancelamento("abc123");
    }

    @Test
    void deveLancarExcecaoSeTransacaoNaoEncontrada() {
        // Arrange
        PublicadorEvento publicador = mock(PublicadorEvento.class);
        TransacaoRepository repo = mock(TransacaoRepository.class);
        when(repo.buscarDataTransacaoPorId("inexistente")).thenReturn(null);

        CancelamentoService service = new CancelamentoService(publicador, repo);

        // Act & Assert
        Assertions.assertThrows(TransacaoNaoEncontradaException.class, () -> {
            service.cancelar("inexistente", "Não existe");
        });
    }

    @Test
    void deveLancarExcecaoSeForaDoPrazo() {
        // Arrange
        PublicadorEvento publicador = mock(PublicadorEvento.class);
        TransacaoRepository repo = mock(TransacaoRepository.class);
        when(repo.buscarDataTransacaoPorId("expirada123")).thenReturn(LocalDateTime.now().minusDays(10));

        CancelamentoService service = new CancelamentoService(publicador, repo);

        // Act & Assert
        Assertions.assertThrows(CancelamentoForaDoPrazoException.class, () -> {
            service.cancelar("expirada123", "Prazo excedido");
        });
    }
}

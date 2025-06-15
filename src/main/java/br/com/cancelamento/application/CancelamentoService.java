package br.com.cancelamento.application;

import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import br.com.cancelamento.domain.ports.output.TransacaoRepository;
import br.com.cancelamento.exceptions.CancelamentoForaDoPrazoException;
import br.com.cancelamento.exceptions.TransacaoNaoEncontradaException;

import java.time.LocalDateTime;

// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoService implements CancelamentoUseCase {

    private final PublicadorEvento publicador;
    private final TransacaoRepository transacaoRepository;

    public CancelamentoService(PublicadorEvento publicador, TransacaoRepository transacaoRepository) {
        this.publicador = publicador;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void cancelar(String idTransacao, String mensagem) {
        // Busca a data da transação
        LocalDateTime dataTransacao = transacaoRepository.buscarDataTransacaoPorId(idTransacao);

        if (dataTransacao == null) {
            throw new TransacaoNaoEncontradaException(idTransacao);
        }

        if (dataTransacao.isBefore(LocalDateTime.now().minusDays(7))) {
            throw new CancelamentoForaDoPrazoException(idTransacao);
        }

        System.out.println("Cancelando transação: " + idTransacao + " - " + mensagem);
        publicador.publicarEventoCancelamento(idTransacao);
    }
}

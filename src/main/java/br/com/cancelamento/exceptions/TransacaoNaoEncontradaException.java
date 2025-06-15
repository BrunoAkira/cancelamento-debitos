package br.com.cancelamento.exceptions;

// Classe principal da aplicação que inicia o Spring Boot
public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String idTransacao) {
        super("Transação com ID " + idTransacao + " não encontrada.");
    }
}

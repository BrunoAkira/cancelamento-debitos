package br.com.cancelamento.exceptions;

// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoForaDoPrazoException extends RuntimeException {
    public CancelamentoForaDoPrazoException(String idTransacao) {
        super("Transação " + idTransacao + " não pode ser cancelada pois passou do prazo de 7 dias.");
    }
}
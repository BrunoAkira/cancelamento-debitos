package br.com.cancelamento.domain.ports.output;

/**
 * Porta de saída responsável por publicar eventos de cancelamento.
 */
public interface PublicadorEvento {
    void publicarEventoCancelamento(String idTransacao);
}

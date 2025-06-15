package br.com.cancelamento.domain.ports.output;

import java.time.LocalDateTime;

/**
 * Porta de saída responsável por buscar informações sobre uma transação.
 */
public interface TransacaoRepository {
    LocalDateTime buscarDataTransacaoPorId(String idTransacao);
}

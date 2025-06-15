package br.com.cancelamento.domain.ports.input;

public interface CancelamentoUseCase {
    void cancelar(String idTransacao, String mensagem);
}

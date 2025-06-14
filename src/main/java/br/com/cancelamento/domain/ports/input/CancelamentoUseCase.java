package br.com.cancelamento.domain.ports.input;

public interface CancelamentoUseCase {
    void cancelar(String numeroConta, String mensagem);
}

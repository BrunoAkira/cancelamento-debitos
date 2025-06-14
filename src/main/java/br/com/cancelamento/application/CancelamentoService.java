package br.com.cancelamento.application;

import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import br.com.cancelamento.domain.ports.output.PublicadorEvento;

public class CancelamentoService implements CancelamentoUseCase {

    private final PublicadorEvento publicador;

    public CancelamentoService(PublicadorEvento publicador) {
        this.publicador = publicador;
    }

    @Override
    public void cancelar(String idDebito, String mensagem) {
        System.out.println("Cancelando débito: " + idDebito + " com mensagem: " + mensagem);
        publicador.publicarEventoCancelamento(idDebito);
    }
}

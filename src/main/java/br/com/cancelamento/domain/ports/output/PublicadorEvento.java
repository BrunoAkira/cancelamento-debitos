package br.com.cancelamento.domain.ports.output;

public interface PublicadorEvento {
    void publicarEventoCancelamento(String idDebito);
}

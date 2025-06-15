package br.com.cancelamento.adapters.input;

// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoRequest {

    private String idTransacao;  // ou idDebito
    private String mensagem;

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

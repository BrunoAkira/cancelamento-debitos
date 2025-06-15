package br.com.cancelamento.adapters.output;

import br.com.cancelamento.domain.ports.output.TransacaoRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Classe principal da aplicação que inicia o Spring Boot
public class TransacaoMockRepository implements TransacaoRepository {

    private final Map<String, LocalDateTime> transacoesSimuladas = new HashMap<>();

    public TransacaoMockRepository() {
        // Simulando algumas transações válidas e inválidas
        transacoesSimuladas.put("123", LocalDateTime.now().minusDays(3)); // dentro do prazo
        transacoesSimuladas.put("456", LocalDateTime.now().minusDays(10)); // fora do prazo
    }

    @Override
    public LocalDateTime buscarDataTransacaoPorId(String idDebito) {
        return transacoesSimuladas.get(idDebito); // Retorna null se não existir
    }
}
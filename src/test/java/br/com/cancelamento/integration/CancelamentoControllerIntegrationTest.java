package br.com.cancelamento.integration;

import br.com.cancelamento.CancelamentoApplication;
import br.com.cancelamento.adapters.input.CancelamentoRequest;
import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import br.com.cancelamento.domain.ports.output.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CancelamentoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @MockBean
// Bean que instancia o adaptador para publicar eventos no SQS
    private PublicadorEvento publicadorEvento;

    @MockBean
// Bean que instancia o repositório mock de transações
    private TransacaoRepository transacaoRepository;

    private String buildUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveRetornarOkParaTransacaoValida() {
        // Arrange
        Mockito.when(transacaoRepository.buscarDataTransacaoPorId("123"))
                .thenReturn(LocalDateTime.now().minusDays(3));

        CancelamentoRequest request = new CancelamentoRequest();
        request.setIdTransacao("123");
        request.setMensagem("teste");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CancelamentoRequest> entity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl("/api/cancelar"), entity, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

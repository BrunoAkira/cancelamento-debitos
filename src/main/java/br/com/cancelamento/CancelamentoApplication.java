package br.com.cancelamento;

import br.com.cancelamento.adapters.output.SqsPublicadorAdapter;
import br.com.cancelamento.adapters.output.TransacaoMockRepository;
import br.com.cancelamento.application.CancelamentoService;
import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import br.com.cancelamento.domain.ports.output.TransacaoRepository;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// Classe principal da aplicação que inicia o Spring Boot
public class CancelamentoApplication {

// Método principal que inicia a aplicação Spring Boot
    public static void main(String[] args) {
        SpringApplication.run(CancelamentoApplication.class, args);
    }

    @Bean
// Bean que configura o cliente SQS com a região desejada
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.standard()
            .withRegion(Regions.SA_EAST_1)
            .build();
    }

    @Bean
// Bean que configura o cliente SQS com a região desejada
    public PublicadorEvento publicadorEvento(AmazonSQS sqs) {
        String queueUrl = "https://sqs.sa-east-1.amazonaws.com/123456789012/minha-fila";
        return new SqsPublicadorAdapter(sqs, queueUrl);
    }

    @Bean
// Bean que instancia o repositório mock de transações
    public TransacaoRepository transacaoRepository() {
        return new TransacaoMockRepository();
    }

    @Bean
// Bean que instancia o adaptador para publicar eventos no SQS
    public CancelamentoUseCase cancelamentoUseCase(PublicadorEvento publicadorEvento, TransacaoRepository transacaoRepository) {
        return new CancelamentoService(publicadorEvento, transacaoRepository);
    }
}
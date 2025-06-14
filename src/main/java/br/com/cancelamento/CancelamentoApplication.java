
package br.com.cancelamento;

import br.com.cancelamento.application.CancelamentoService;
import br.com.cancelamento.domain.ports.input.CancelamentoUseCase;
import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import br.com.cancelamento.adapters.output.SqsPublicadorAdapter;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CancelamentoApplication {

    @Value("${aws.account-id}")
    private String accountId;

    @Value("${aws.queue-name}")
    private String queueName;

    @Value("${aws.region}")
    private String region;

    public static void main(String[] args) {
        SpringApplication.run(CancelamentoApplication.class, args);
    }

    @Bean
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    public PublicadorEvento publicadorEvento(AmazonSQS sqs) {
        String queueUrl = String.format("https://sqs.%s.amazonaws.com/%s/%s", region, accountId, queueName);
        return new SqsPublicadorAdapter(sqs, queueUrl);
    }

    @Bean
    public CancelamentoUseCase cancelamentoUseCase(PublicadorEvento publicadorEvento) {
        return new CancelamentoService(publicadorEvento);
    }
}

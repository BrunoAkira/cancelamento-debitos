package br.com.cancelamento.adapters.output;

import br.com.cancelamento.domain.ports.output.PublicadorEvento;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SqsPublicadorAdapter implements PublicadorEvento {

    private final AmazonSQS sqsClient;
    private final String queueUrl;

    public SqsPublicadorAdapter(AmazonSQS sqsClient, String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    @Override
    public void publicarEventoCancelamento(String idDebito) {
        String mensagem = "{\"evento\":\"cancelamento\",\"id\":\"" + idDebito + "\"}";
        SendMessageRequest request = new SendMessageRequest(queueUrl, mensagem);
        sqsClient.sendMessage(request);
        System.out.println("Mensagem enviada para SQS: " + mensagem);
    }
}

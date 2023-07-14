package com.algamoney.api.listener;

import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoaMessage;
import com.algamoney.api.usecase.telefone.CadastraTelefone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static com.algamoney.api.config.amqp.mapping.Queues.SALVA_TELEFONE_PESSOA_QUEUE;


@Component
@Slf4j
@RequiredArgsConstructor
public class SalvarTelefoneListener {
    private final CadastraTelefone cadastraTelefone;

    @RabbitListener(queues = SALVA_TELEFONE_PESSOA_QUEUE)
    public void dequeue(final Message<EventMessage<SalvarTelefonePessoaMessage>> message) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("SalvarTelefoneListener");

        try {
            log.info("SalvarTelefoneListener a new message has arrived: {}", message.getPayload().getTrackingId());

            execute(message.getPayload());

            log.info("SalvarTelefoneListener message process {} has been finished ", message.getPayload().getTrackingId());

        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        } finally {
            stopWatch.stop();
            log.debug(stopWatch.prettyPrint());
        }
    }

    private void execute(EventMessage<SalvarTelefonePessoaMessage> message) {
        SalvarTelefonePessoaMessage salvarTelefonePessoaMessage = message.getContent().stream().findFirst().orElseThrow(() -> new RuntimeException("Mensagem sem content"));
        cadastraTelefone.executar(salvarTelefonePessoaMessage);
    }
}



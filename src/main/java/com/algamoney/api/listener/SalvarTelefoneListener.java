package com.algamoney.api.listener;


import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoa;
import com.algamoney.api.exception.PessoaNotFoundException;
import com.algamoney.api.usecase.telefone.CadastrarTelefone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static com.algamoney.api.config.amqp.Queues.SALVA_TELEFONE_PESSOA_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class SalvarTelefoneListener {
    private final ObjectMapper objectMapper;
    private final CadastrarTelefone cadastrarTelefone;

    @RabbitListener(queues = SALVA_TELEFONE_PESSOA_QUEUE)
    public void dequeue(final Message message) {
        SalvarTelefonePessoa salvarTelefonePessoa = null;
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("SalvarTelefoneListener");

        try {
            final EventMessage<SalvarTelefonePessoa> eventMessage = this.objectMapper
                    .readValue(message.getPayload().toString(), new TypeReference<EventMessage<SalvarTelefonePessoa>>() {});

            log.info("A new message has arrived: {}", eventMessage.getTrackingId());

            salvarTelefonePessoa = eventMessage.getContent();
            execute(salvarTelefonePessoa);
            log.info("Message process {} has been finished ", eventMessage.getTrackingId());

        } catch (PessoaNotFoundException e) {
            log.error("Pessoa = " + salvarTelefonePessoa.getId() + ": " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        } finally {
            stopWatch.stop();
            log.debug(stopWatch.prettyPrint());
        }
    }

    private void execute(SalvarTelefonePessoa message) {
        cadastrarTelefone.executar(message);
    }
}

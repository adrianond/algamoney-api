package com.algamoney.api.listener;


import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.http.domain.CargaPessoaDTO;
import com.algamoney.api.usecase.pessoa.PersistirCargaPessoa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

import static com.algamoney.api.config.amqp.mapping.Queues.CARGA_PESSOA_QUEUE;


@Component
@Slf4j
@RequiredArgsConstructor
public class CargaPessoaListener {
    private final PersistirCargaPessoa persistirCargaPessoa;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = CARGA_PESSOA_QUEUE, concurrency = "${app.algamoney.rabbit.config.consumers}")
    public void dequeue(final Message message) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("CargaPessoaListener");

        try {
            log.info("CargaPessoaListener a new message has arrived: {}", message);

            final EventMessage<CargaPessoaDTO> eventMessage = this.objectMapper
                    .readValue(message.getPayload().toString(), new TypeReference<EventMessage<CargaPessoaDTO>>() {
                    });

            List<CargaPessoaDTO> list = eventMessage.getContent().stream().collect(Collectors.toList());

            execute(list);

            log.info("A new message has arrived: {}", eventMessage.getTrackingId());
        } catch (final Exception e) {
            log.error("An error has occurred whilst deserialize the message.", e);
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        } finally {
            stopWatch.stop();
            log.debug(stopWatch.prettyPrint());
        }
    }

    private void execute(List<CargaPessoaDTO> list) {
        if (CollectionUtils.isEmpty(list))
            new RuntimeException("Mensagem sem content");

        persistirCargaPessoa.executar(list);
    }
}


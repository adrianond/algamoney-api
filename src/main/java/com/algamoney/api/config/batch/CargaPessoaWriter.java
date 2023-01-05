package com.algamoney.api.config.batch;

import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.config.amqp.domain.builder.EventMessageBuilder;
import com.algamoney.api.config.amqp.publish.Postman;
import com.algamoney.api.http.domain.CargaPessoaDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.algamoney.api.config.amqp.mapping.Queues.CARGA_PESSOA_QUEUE;
import static java.lang.String.format;

@Component
@AllArgsConstructor
@Slf4j
public class CargaPessoaWriter implements ItemWriter<CargaPessoaDTO> {
    private final Postman sender;

    @Override
    public void write(List<? extends CargaPessoaDTO> messages) {
        String trackingId = format("SAVE-%d", Instant.now().toEpochMilli());
        int size = messages.size();

        EventMessage<CargaPessoaDTO> event = new EventMessageBuilder<CargaPessoaDTO>()
                .trackingId(trackingId)
                .content(new ArrayList<>(messages))
                .creationDate()
                .build();

        log.info("Enfileirando {} com {} registros", trackingId, size);
        sender.fireEvent(event, CARGA_PESSOA_QUEUE);

    }
}


package com.algamoney.api.usecase.telefone;


import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoaMessage;
import com.algamoney.api.config.amqp.domain.builder.EventMessageBuilder;
import com.algamoney.api.config.amqp.mapping.Queues;
import com.algamoney.api.config.amqp.publish.Postman;
import com.algamoney.api.http.domain.TelefoneDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class SalvarTelefoneAssincrono {
    private final Postman sender;

    public void executar(Long id, List<TelefoneDTO> telefones) {
        SalvarTelefonePessoaMessage salvarTelefonePessoaMessage = new SalvarTelefonePessoaMessage(id, telefones);
        String trackingId = String.format("SALVAR-TELEFONE-%d", salvarTelefonePessoaMessage.getId());
        EventMessage<SalvarTelefonePessoaMessage> event = new EventMessageBuilder()
                .trackingId(trackingId)
                .content(Collections.singletonList(salvarTelefonePessoaMessage))
                .creationDate()
                .build();

        log.info("Enfileirando {}", trackingId);
        sender.fireEvent(event, Queues.SALVA_TELEFONE_PESSOA_QUEUE);
    }
}

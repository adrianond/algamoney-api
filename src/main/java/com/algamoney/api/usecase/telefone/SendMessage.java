package com.algamoney.api.usecase.telefone;

import com.algamoney.api.config.amqp.EventMessage;
import com.algamoney.api.config.amqp.RabbitMQConfigurationProperties;
import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoa;
import com.algamoney.api.exception.QueueNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendMessage {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitMQConfigurationProperties properties;
    private Map<String, Object> propertObjectMap  = new HashMap<>();
    private final ObjectMapper objectMapper;

    public void enviar(SalvarTelefonePessoa msg, String queue) {
        String trackingId = String.format("SALVAR-TELEFONE-%d", msg.getId());
        EventMessage<SalvarTelefonePessoa> eventMessage = new EventMessage<>();
        eventMessage.setContent(msg);
        eventMessage.setTrackingId(trackingId);
        eventMessage.setCreationDate(LocalDateTime.now());

        log.info("Enfileirando {}", trackingId);
        RabbitMQConfigurationProperties.Binding binding = properties.getQueueByName(queue)
                .orElseThrow(() -> new QueueNotFoundException(String.format("Unable to find the given queue: '%s'", queue)));
        log.debug("enqueueing message: {} at: {} queue, at: {}", eventMessage, queue, LocalDateTime.now());

        final String exchange = binding.getTopic();
        log.debug("Queuing {} to {}", eventMessage, exchange);

        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        final Message message = rabbitTemplate.getMessageConverter().toMessage(stringifyRabbitMessage(eventMessage), get());
        rabbitTemplate.send(exchange, binding.getRoutingKey(), message);
    }

    private String stringifyRabbitMessage(final Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("object couldnt be serialized as a json string", e);
        }
        return data.toString();
    }

    public MessageProperties get() {
        final MessagePropertiesBuilder messagePropertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertObjectMap.forEach(messagePropertiesBuilder::setHeader);
        return messagePropertiesBuilder.build();
    }
}


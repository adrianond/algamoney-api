package com.algamoney.api.config.amqp;

import com.algamoney.api.config.amqp.mapping.RabbitMQConfigurationProperties;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQPublish {

    private final RabbitAdmin rabbitAdmin;

    private final ObjectMapper objectMapper;

    public void enqueue(final MessageContext context) {
        final RabbitMQConfigurationProperties.Binding binding = context.getRabbitMQBiding();
        final Object data = context.getRabbitMessage();
        final String exchange = binding.getTopic();
        log.debug("Queuing {} to {}", data, exchange);

        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        final Message message = rabbitTemplate.getMessageConverter().toMessage(stringifyRabbitMessage(data), getProperties(context));
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

    private MessageProperties getProperties(final MessageContext context) {
        return MessagePropertiesBuilder.fromProperties(context.getProperties()).build();
    }
}

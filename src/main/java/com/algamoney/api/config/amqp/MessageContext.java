package com.algamoney.api.config.amqp;

import com.algamoney.api.config.amqp.mapping.RabbitMQConfigurationProperties;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

import java.util.HashMap;
import java.util.Map;

public final class MessageContext {

    private RabbitMQConfigurationProperties.Binding binding;
    private Map<String, Object> properties;
    private Object rabbitMessage;

    public MessageContext(final Object rabbitMessage, final RabbitMQConfigurationProperties.Binding mqMapping) {
        this.rabbitMessage = rabbitMessage;
        this.binding = mqMapping;
        this.properties = new HashMap<>();
    }

    public MessageContext(final Object rabbitMessage, final RabbitMQConfigurationProperties.Binding mqMapping, final Map<String, Object> properties) {
        this.rabbitMessage = rabbitMessage;
        this.binding = mqMapping;
        this.properties = properties;
    }

    public RabbitMQConfigurationProperties.Binding getRabbitMQBiding() {
        return binding;
    }

    public MessageProperties getProperties() {
        final MessagePropertiesBuilder messagePropertiesBuilder = MessagePropertiesBuilder.newInstance();
        properties.forEach(messagePropertiesBuilder::setHeader);
        return messagePropertiesBuilder.build();
    }

    public Object getRabbitMessage() {
        return rabbitMessage;
    }

}

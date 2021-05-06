package com.algamoney.api.config.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumerConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(final RabbitMQConfigurationProperties configurationProperties) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(configurationProperties.getConfig().getConsumers());
        factory.setDefaultRequeueRejected(false);

        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(configurationProperties.getConfig().getInitialInterval());
        policy.setMaxInterval(configurationProperties.getConfig().getMaxInterval());
        policy.setMultiplier(configurationProperties.getConfig().getMultiplier());

        factory.setAdviceChain(
                org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
                        .stateless()
                        .maxAttempts(configurationProperties.getConfig().getRetries())
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .backOffPolicy(policy)
                        .build());

        return factory;
    }
}

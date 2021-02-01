package com.advertisementproject.userservice.messagebroker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig {
    @Bean
    public Queue confirmationTokenQueue() {
        return new Queue("confirmationToken", false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("emailDetails", false);
    }
}

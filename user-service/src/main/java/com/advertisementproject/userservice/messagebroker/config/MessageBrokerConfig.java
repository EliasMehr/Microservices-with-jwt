package com.advertisementproject.userservice.messagebroker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations for RabbitMQ message broker including specifications for queues to be used for cross microservice
 * communication
 */
@Configuration
public class MessageBrokerConfig {
    /**
     * Direct messaging queue configuration bean
     *
     * @return direct messaging queue to inform Confirmation Token Service application that it should create a new
     * token for a user id
     */
    @Bean
    public Queue confirmationTokenQueue() {
        return new Queue("confirmationToken", false);
    }

    /**
     * Direct messaging queue configuration bean
     *
     * @return direct messaging queue to inform User Service that it should enable a user.
     */
    @Bean
    public Queue enableUserQueue() {
        return new Queue("enableUser", false);
    }

    /**
     * Direct messaging queue configuration bean
     *
     * @return direct messaging queue to supply Email Service application with email details for sending a confirmation
     * link email to a specific user.
     */
    @Bean
    public Queue emailQueue() {
        return new Queue("emailDetails", false);
    }
}

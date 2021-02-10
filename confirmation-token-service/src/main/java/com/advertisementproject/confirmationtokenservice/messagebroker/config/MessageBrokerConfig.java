package com.advertisementproject.confirmationtokenservice.messagebroker.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations for RabbitMQ message broker including specifications for queues and exchanges to be used for
 * cross microservice communication
 */
@Configuration
public class MessageBrokerConfig {

    /**
     * Declarables configuration bean
     *
     * @return a declarables object including a queue to listen to for when to delete confirmation tokens, a fanout
     * exchange which User Service application can send messages to when a user is deleted and a binding which copies
     * message in the "user.delete" exchange and sends it to "confirmationTokenDelete" which the this application can
     * listen for.
     */
    @Bean
    public Declarables userDeleteFanoutBinding() {
        Queue queue = new Queue("confirmationTokenDelete", false);
        FanoutExchange fanoutExchange = new FanoutExchange("user.delete");

        return new Declarables(
                queue,
                fanoutExchange,
                BindingBuilder.bind(queue).to(fanoutExchange));
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
     * @return direct messaging queue to inform Email Service that a new token has been created for a user id.
     */
    @Bean
    public Queue emailTokenQueue() {
        return new Queue("emailToken", false);
    }

    /**
     * Direct messaging queue configuration bean
     *
     * @return direct messaging queue to inform Permissions Service that it should create permissions for a specific
     * user id.
     */
    @Bean
    public Queue permissionsAddQueue() {
        return new Queue("permissionsAdd", false);
    }
}
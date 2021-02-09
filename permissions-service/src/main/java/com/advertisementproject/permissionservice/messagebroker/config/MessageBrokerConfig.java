package com.advertisementproject.permissionservice.messagebroker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
     * Custom object mapper bean for getting an object mapper that can handle time based variables like Instant
     *
     * @return a configured ObjectMapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Queue configuration bean
     *
     * @return queue for direct messaging to be used when permissions should be added for a supplied user id
     */
    @Bean
    public Queue permissionAddQueue() {
        return new Queue("permissionAdd", false);
    }


    //User delete RabbitMQ config

    /**
     * Queue configuration bean
     *
     * @return queue for receiving messages with a user id to know when a user's permissions should be deleted
     */
    @Bean
    public Queue permissionDeleteQueue() {
        return new Queue("permissionUserDelete", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages informing that a user's information should be deleted, which can be received
     * by multiple applications that connect to the exchange with a queue
     */
    @Bean
    public FanoutExchange userDeleteExchange() {
        return new FanoutExchange("user.delete");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between permissionsDeleteQueue and userDeleteExchange, which means that when a message is sent to
     * "user.delete", a copy of that message will be sent to "permissionsUserDelete", which the application can listen for
     */
    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(permissionDeleteQueue()).to(userDeleteExchange());
    }

}

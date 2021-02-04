package com.advertisementproject.permissionsservice.messagebroker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public Queue permissionsAddQueue() { return new Queue("permissionsAdd", false); }

    //User delete RabbitMQ config
    @Bean
    public Queue permissionsDeleteQueue() { return new Queue("permissionsUserDelete", false); }

    @Bean
    public FanoutExchange userDeleteExchange() { return new FanoutExchange("user.delete"); }

    @Bean
    public Binding userDeleteBinding() { return BindingBuilder.bind(permissionsDeleteQueue()).to(userDeleteExchange()); }

}

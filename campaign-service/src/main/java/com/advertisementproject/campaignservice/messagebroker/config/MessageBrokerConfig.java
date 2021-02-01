package com.advertisementproject.campaignservice.messagebroker.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig {

    //User delete RabbitMQ config
    @Bean
    public Queue deleteQueue(){
        return new Queue("campaignsUserDelete", false);
    }

    @Bean
    public FanoutExchange userDeleteExchange(){
        return new FanoutExchange("user.delete");
    }

    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(userDeleteExchange());
    }

    //company save/update RabbitMQ config
    @Bean
    public Queue companyQueue(){
        return new Queue("campaignsCompany", false);
    }

    @Bean
    public FanoutExchange companyExchange(){
        return new FanoutExchange("company");
    }

    @Bean
    public Binding userAddBinding() {
        return BindingBuilder.bind(companyQueue()).to(companyExchange());
    }
}
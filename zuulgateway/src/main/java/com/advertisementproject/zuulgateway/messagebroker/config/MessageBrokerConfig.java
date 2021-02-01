package com.advertisementproject.zuulgateway.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageBrokerConfig {

    //user save/update RabbitMQ config
    @Bean
    public Queue userQueue(){ return new Queue("zuulUser", false); }

    @Bean
    public FanoutExchange userExchange(){
        return new FanoutExchange("user");
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange());
    }


    //user delete RabbitMQ config
    @Bean
    public Queue userDeleteQueue(){ return new Queue("zuulUserDelete", false); }

    @Bean
    public FanoutExchange userDeleteExchange(){
        return new FanoutExchange("user.delete");
    }

    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange());
    }


    //permissions save/update RabbitMQ config
    @Bean
    public Queue permissionsQueue(){ return new Queue("zuulPermissions", false); }

    @Bean
    public FanoutExchange permissionsExchange(){
        return new FanoutExchange("permissions");
    }

    @Bean
    public Binding permissionsBinding() {
        return BindingBuilder.bind(permissionsQueue()).to(permissionsExchange());
    }


    //permissions delete RabbitMQ config
    @Bean
    public Queue permissionsDeleteQueue(){ return new Queue("zuulPermissionsDelete", false); }

    @Bean
    public FanoutExchange permissionsDeleteExchange(){
        return new FanoutExchange("permissions.delete");
    }

    @Bean
    public Binding permissionsDeleteBinding() {
        return BindingBuilder.bind(permissionsDeleteQueue()).to(permissionsDeleteExchange());
    }

}

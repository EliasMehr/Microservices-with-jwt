package com.advertisementproject.zuulgateway.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations for RabbitMQ message broker including specifications for queues and exchanges to be used for
 * cross microservice communication.
 */
@Configuration
public class MessageBrokerConfig {

    //user save/update RabbitMQ config

    /**
     * Queue configuration bean
     * @return queue for receiving messages with a user object to create or update in the database.
     */
    @Bean
    public Queue userQueue(){ return new Queue("zuulUser", false); }

    /**
     * Fanout exchange configuration bean
     * @return fanout exchange for messages including a user object to add/update, which can be received
     * by multiple applications that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange userExchange(){
        return new FanoutExchange("user");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     * @return binding between userQueue and userExchange, which means that when a message is sent to "user",
     * a copy of that message will be sent to "zuulUser", which the application can listen for
     */
    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange());
    }


    //user delete RabbitMQ config

    /**
     * Queue configuration bean
     * @return queue for receiving messages with a user id to know when a user should be deleted
     */
    @Bean
    public Queue userDeleteQueue(){ return new Queue("zuulUserDelete", false); }

    /**
     * Fanout exchange configuration bean
     * @return fanout exchange for messages informing that a user's information should be deleted, which can be received
     * by multiple applications that connect to the exchange with a queue
     */
    @Bean
    public FanoutExchange userDeleteExchange(){
        return new FanoutExchange("user.delete");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     * @return binding between userDeleteQueue and userDeleteExchange, which means that when a message is sent to
     * "user.delete", a copy of that message will be sent to "zuulUserDelete", which the application can listen for
     */
    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange());
    }


    //permissions save/update RabbitMQ config

    /**
     * Queue configuration bean
     * @return queue for receiving messages with a permissions object to create or update in the database.
     */
    @Bean
    public Queue permissionsQueue(){ return new Queue("zuulPermissions", false); }

    /**
     * Fanout exchange configuration bean
     * @return fanout exchange for messages including a permissions object to add/update, which can be received
     * by multiple applications that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange permissionsExchange(){
        return new FanoutExchange("permissions");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     * @return binding between permissionsQueue and permissionsExchange, which means that when a message is sent to
     * "permissions", a copy of that message will be sent to "zuulPermissions", which the application can listen for
     */
    @Bean
    public Binding permissionsBinding() {
        return BindingBuilder.bind(permissionsQueue()).to(permissionsExchange());
    }


    //permissions delete RabbitMQ config

    /**
     * Queue configuration bean
     * @return queue for receiving messages with a permissions id to know when a permissions entity should be deleted
     */
    @Bean
    public Queue permissionsDeleteQueue(){ return new Queue("zuulPermissionsDelete", false); }

    /**
     * Fanout exchange configuration bean
     * @return fanout exchange for messages including a permissions id, informing that a permissions entity should be
     * deleted, which can be received by multiple applications that connect to the exchange with a queue
     */
    @Bean
    public FanoutExchange permissionsDeleteExchange(){
        return new FanoutExchange("permissions.delete");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     * @return binding between permissionsDeleteQueue and permissionsDeleteExchange, which means that when a message is
     * sent to "permissions.delete", a copy of that message will be sent to "zuulPermissionsDelete", which the
     * application can listen for
     */
    @Bean
    public Binding permissionsDeleteBinding() {
        return BindingBuilder.bind(permissionsDeleteQueue()).to(permissionsDeleteExchange());
    }

}

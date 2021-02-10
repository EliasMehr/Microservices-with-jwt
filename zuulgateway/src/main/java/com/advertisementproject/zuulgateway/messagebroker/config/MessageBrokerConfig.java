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
     *
     * @return queue for receiving messages with a user object to create or update in the database.
     */
    @Bean
    public Queue userQueue() {
        return new Queue("zuulUser", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages including a user object to add/update, which can be received
     * by multiple applications that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange userExchange() {
        return new FanoutExchange("user");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
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
     *
     * @return queue for receiving messages with a user id to know when a user should be deleted
     */
    @Bean
    public Queue userDeleteQueue() {
        return new Queue("zuulUserDelete", false);
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
     * @return binding between userDeleteQueue and userDeleteExchange, which means that when a message is sent to
     * "user.delete", a copy of that message will be sent to "zuulUserDelete", which the application can listen for
     */
    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange());
    }


    //permission save/update RabbitMQ config

    /**
     * Queue configuration bean
     *
     * @return queue for receiving messages with a permission object to create or update in the database.
     */
    @Bean
    public Queue permissionQueue() {
        return new Queue("zuulPermission", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages including a permission object to add/update, which can be received
     * by multiple applications that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange permissionExchange() {
        return new FanoutExchange("permission");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between permissionQueue and permissionExchange, which means that when a message is sent to
     * "permission", a copy of that message will be sent to "zuulPermission", which the application can listen for
     */
    @Bean
    public Binding permissionBinding() {
        return BindingBuilder.bind(permissionQueue()).to(permissionExchange());
    }


    //permission delete RabbitMQ config

    /**
     * Queue configuration bean
     *
     * @return queue for receiving messages with a permission id to know when a permission entity should be deleted
     */
    @Bean
    public Queue permissionDeleteQueue() {
        return new Queue("zuulPermissionDelete", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages including a permission id, informing that a permission entity should be
     * deleted, which can be received by multiple applications that connect to the exchange with a queue
     */
    @Bean
    public FanoutExchange permissionDeleteExchange() {
        return new FanoutExchange("permission.delete");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between permissionDeleteQueue and permissionDeleteExchange, which means that when a message is
     * sent to "permission.delete", a copy of that message will be sent to "zuulPermissionDelete", which the
     * application can listen for
     */
    @Bean
    public Binding permissionDeleteBinding() {
        return BindingBuilder.bind(permissionDeleteQueue()).to(permissionDeleteExchange());
    }

}

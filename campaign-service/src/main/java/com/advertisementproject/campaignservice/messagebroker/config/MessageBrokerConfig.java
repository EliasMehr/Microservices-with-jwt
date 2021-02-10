package com.advertisementproject.campaignservice.messagebroker.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations for RabbitMQ message broker including specifications for queues and exchanges to be used for
 * cross microservice communication
 */
@Configuration
public class MessageBrokerConfig {

    //company delete RabbitMQ config

    /**
     * Queue configuration bean
     *
     * @return queue for receiving messages with a company id to know when a company's campaigns should be deleted
     */
    @Bean
    public Queue deleteQueue() {
        return new Queue("campaignsUserDelete", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages informing that a user's information should be deleted, which can be received
     * by multiple applications that connect to the exchange with a queue. User id is identical to company id
     */
    @Bean
    public FanoutExchange userDeleteExchange() {
        return new FanoutExchange("user.delete");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between campaignsUserDeleteQueue and userDeleteExchange, which means that when a message is sent to
     * "user.delete", a copy of that message will be sent to "campaignsUserDelete", which the application can listen for
     */
    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(userDeleteExchange());
    }

    //company save/update RabbitMQ config

    /**
     * Queue configuration bean
     *
     * @return queue for receiving messages with a company object to update in the company database
     */
    @Bean
    public Queue companyQueue() {
        return new Queue("campaignsCompany", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages including a company object, which can be received by multiple applications
     * that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange companyExchange() {
        return new FanoutExchange("company");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between companyQueue and companyExchange, which means that when a message is sent to "company",
     * a copy of that message will be sent to "campaignsCompany", which the application can listen for
     */
    @Bean
    public Binding userAddBinding() {
        return BindingBuilder.bind(companyQueue()).to(companyExchange());
    }
}
package com.advertisementproject.campaignservice.messagebroker.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig {

    @Bean
    public Declarables userDeleteFanoutBinding() {
        Queue queue = new Queue("campaignsDelete", false);
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.user.delete");

        return new Declarables(
                queue,
                fanoutExchange,
                BindingBuilder.bind(queue).to(fanoutExchange));
    }
}
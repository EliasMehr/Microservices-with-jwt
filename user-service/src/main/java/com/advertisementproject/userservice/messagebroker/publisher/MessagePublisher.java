package com.advertisementproject.userservice.messagebroker.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Bean
    public Queue permissionsAddQueue() {
        return new Queue("permissionsAdd", true);
    }

    @Bean
    public Queue permissionsDeleteQueue() {
        return new Queue("permissionsDelete", true);
    }

    @Bean
    public Queue confirmationTokenQueue() {
        return new Queue("confirmationToken", false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email", false);
    }
}

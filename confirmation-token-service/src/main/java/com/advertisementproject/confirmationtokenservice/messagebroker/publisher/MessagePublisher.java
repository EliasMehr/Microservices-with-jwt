package com.advertisementproject.confirmationtokenservice.messagebroker.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Object message) {
        try {
            String messageString = new ObjectMapper().writeValueAsString(message);
            log.info("[MESSAGE BROKER] Sending message to " + queueName + ": " + messageString);
            rabbitTemplate.convertAndSend(queueName, messageString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email", false);
    }

    @Bean
    public Queue enableUserQueue() {
        return new Queue("enableUser", false);
    }

    @Bean
    public Queue permissionsAddQueue() {
        return new Queue("permissionsAdd", true);
    }

}

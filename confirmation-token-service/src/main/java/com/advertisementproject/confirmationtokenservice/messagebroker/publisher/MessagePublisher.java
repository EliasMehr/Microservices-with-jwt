package com.advertisementproject.confirmationtokenservice.messagebroker.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendUserIdMessage(String queueName, UUID userId) {
        log.info("[MESSAGE BROKER] Sending userId to " + queueName + ": " + userId);
        rabbitTemplate.convertAndSend(queueName, userId);
    }

    public void sendEmailTokenMessage(String token) {
        String queueName = "emailToken";
        log.info("[MESSAGE BROKER] Sending token to " + queueName + ": " + token);
        rabbitTemplate.convertAndSend(queueName, token);
    }

    @Bean
    public Queue enableUserQueue() {
        return new Queue("enableUser", false);
    }

    @Bean
    public Queue emailTokenQueue() {
        return new Queue("emailToken", false);
    }

    @Bean
    public Queue permissionsAddQueue() {
        return new Queue("permissionsAdd", true);
    }

}

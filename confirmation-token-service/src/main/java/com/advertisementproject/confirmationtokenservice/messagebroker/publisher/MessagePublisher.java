package com.advertisementproject.confirmationtokenservice.messagebroker.publisher;

import com.advertisementproject.confirmationtokenservice.messagebroker.dto.TokenMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessagePublisher is a service for publishing messages asynchronously to other microservices via RabbitMQ.
 * RabbitTemplate is used to send the message and ObjectMapper is used to convert objects to JSON
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Sends a user id to a specified queue which one or more microservices can listen to for updates.
     * @param queueName the name of the queue to send a user id to.
     * @param userId the user id that should be sent to one or more microservices.
     */
    public void sendUserIdMessage(String queueName, UUID userId) {
        log.info("[MESSAGE BROKER] Sending userId to " + queueName + ": " + userId);
        rabbitTemplate.convertAndSend(queueName, userId);
    }

    /**
     * Sends token and user id as a JSON string to the queue "emailToken" which Email Service application listens to.
     * @param tokenMessage dto message including token string and user id
     */
    public void sendTokenMessage(TokenMessage tokenMessage) {
        String queueName = "emailToken";
        try {
            String messageString = new ObjectMapper().writeValueAsString(tokenMessage);
            log.info("[MESSAGE BROKER] Sending token to " + queueName + ": " + messageString);
            rabbitTemplate.convertAndSend(queueName, messageString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

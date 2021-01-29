package com.advertisementproject.userservice.messagebroker.publisher;

import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
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

    public void sendFanoutDeleteUserMessage(UUID userId){
        rabbitTemplate.convertAndSend("fanout.user.delete", "", userId);
    }

    public void sendUserIdMessage(String queueName, UUID userId){
        log.info("[MESSAGE BROKER] Sending userId to " + queueName + ": " + userId);
        rabbitTemplate.convertAndSend(queueName, userId);
    }

    public void sendEmailDetailsMessage(EmailDetailsMessage message) {
        String queueName = "emailDetails";
        try {
            String messageString = new ObjectMapper().writeValueAsString(message);
            log.info("[MESSAGE BROKER] Sending message to " + queueName + ": " + messageString);
            rabbitTemplate.convertAndSend(queueName, messageString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Queue confirmationTokenQueue() {
        return new Queue("confirmationToken", false);
    }

    @Bean
    public Queue emailQueue() { return new Queue("emailDetails", false); }
}

package com.advertisementproject.emailservice.messagebroker.listener;

import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final EmailService emailService;

    @RabbitListener(queues = "emailDetails")
    public void emailDetailsListener(String messageObject){
        try {
            EmailDetailsMessage emailDetailsMessage = new ObjectMapper().readValue(messageObject, EmailDetailsMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + emailDetailsMessage);

            emailService.saveDetailsOrSendEmailIfReady(emailDetailsMessage);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "emailToken")
    public void emailTokenListener(String token){
        log.info("[MESSAGE BROKER] Received token: " + token);
        emailService.saveTokenOrSendEmailIfReady(token);
    }
}

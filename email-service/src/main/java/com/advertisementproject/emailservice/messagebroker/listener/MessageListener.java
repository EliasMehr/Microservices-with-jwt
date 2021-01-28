package com.advertisementproject.emailservice.messagebroker.listener;

import com.advertisementproject.emailservice.messagebroker.dto.EmailMessage;
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

    @RabbitListener(queues = "email")
    public void confirmationTokenlistener(String messageObject){
        try {
            EmailMessage emailMessage = new ObjectMapper().readValue(messageObject, EmailMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + emailMessage);

            emailService.sendConfirmationLinkEmail(
                    emailMessage.getEmail(), emailMessage.getName(), emailMessage.getToken());

        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }
}

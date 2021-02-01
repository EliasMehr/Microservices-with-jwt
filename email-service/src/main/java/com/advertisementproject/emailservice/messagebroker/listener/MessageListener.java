package com.advertisementproject.emailservice.messagebroker.listener;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.emailservice.service.interfaces.EmailDetailsService;
import com.advertisementproject.emailservice.service.interfaces.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final EmailDetailsService emailDetailsService;
    private final EmailService emailService;

    @RabbitListener(queues = "emailDetails")
    public void emailDetailsListener(String messageObject){
        try {
            EmailDetailsMessage emailDetailsMessage = new ObjectMapper().readValue(messageObject, EmailDetailsMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + emailDetailsMessage);

            emailDetailsService.saveDetails(emailDetailsMessage);
            sendEmailAndDeletePostIfCompleteInformation(emailDetailsMessage.getUserId());
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "emailToken")
    public void emailTokenListener(String messageObject){
        try {
            TokenMessage tokenMessage = new ObjectMapper().readValue(messageObject, TokenMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + tokenMessage);
            emailDetailsService.saveToken(tokenMessage);
            sendEmailAndDeletePostIfCompleteInformation(tokenMessage.getUserId());
        }
        catch (JsonProcessingException e){
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }

    private void sendEmailAndDeletePostIfCompleteInformation(UUID userId) {
        EmailDetails emailDetails = emailDetailsService.getCompleteDetailsOrNull(userId);
        if(emailDetails != null){
            emailService.sendConfirmationLinkEmail(emailDetails.getEmail(), emailDetails.getName(), emailDetails.getToken());
            emailDetailsService.deleteEmailDetails(emailDetails);
        }
    }
}

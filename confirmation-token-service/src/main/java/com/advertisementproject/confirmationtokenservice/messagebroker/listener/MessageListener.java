package com.advertisementproject.confirmationtokenservice.messagebroker.listener;

import com.advertisementproject.confirmationtokenservice.messagebroker.dto.UserMessage;
import com.advertisementproject.confirmationtokenservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.confirmationtokenservice.service.ConfirmationTokenService;
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

    private final ConfirmationTokenService confirmationTokenService;
    private final MessagePublisher messagePublisher;

    @RabbitListener(queues = "confirmationToken")
    public void confirmationTokenlistener(String messageObject){
        try {
            UserMessage userMessage = new ObjectMapper().readValue(messageObject, UserMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + userMessage);

            String token = confirmationTokenService.generateAndSaveToken(userMessage.getUserId());
            userMessage.setToken(token);

            messagePublisher.sendMessage("email", userMessage);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

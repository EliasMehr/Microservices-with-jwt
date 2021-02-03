package com.advertisementproject.confirmationtokenservice.messagebroker.listener;

import com.advertisementproject.confirmationtokenservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.confirmationtokenservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.confirmationtokenservice.service.interfaces.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of ConfirmationTokenService.
 * When a new token is created, this application also sends a message to Email Service application so that an email
 * with a confirmation link including the token can be created and sent to the right user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final ConfirmationTokenService confirmationTokenService;
    private final MessagePublisher messagePublisher;

    /**
     * Listens for when a user has been created, uses that user id to create and save a confirmation token, then
     * informs Email Service application about the new token that has been created.
     * @param userId the user id for which to create a confirmation token.
     */
    @RabbitListener(queues = "confirmationToken")
    public void userIdListener(UUID userId){
            log.info("[MESSAGE BROKER] Received userId: " + userId);
            String token = confirmationTokenService.generateAndSaveToken(userId);
            messagePublisher.sendTokenMessage(new TokenMessage(userId, token));
    }

    /**
     * Listens for when a user has been deleted and then deletes all confirmation tokens for that user id.
     * @param userId the user id for which to delete all tokens.
     */
    @RabbitListener(queues = "confirmationTokenDelete")
    public void confirmationTokenDeleteListener(UUID userId){
        log.info("[MESSAGE BROKER] Received delete message for userId: " + userId);
        confirmationTokenService.deleteAllConfirmationTokensByUserId(userId);
    }
}

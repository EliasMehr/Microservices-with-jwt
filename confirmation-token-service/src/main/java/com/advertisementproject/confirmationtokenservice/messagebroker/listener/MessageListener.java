package com.advertisementproject.confirmationtokenservice.messagebroker.listener;

import com.advertisementproject.confirmationtokenservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.confirmationtokenservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.confirmationtokenservice.service.interfaces.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final ConfirmationTokenService confirmationTokenService;
    private final MessagePublisher messagePublisher;

    @RabbitListener(queues = "confirmationToken")
    public void userIdListener(UUID userId){
            log.info("[MESSAGE BROKER] Received userId: " + userId);
            String token = confirmationTokenService.generateAndSaveToken(userId);
            messagePublisher.sendTokenMessage(new TokenMessage(userId, token));
    }

    @RabbitListener(queues = "confirmationTokenDelete")
    public void confirmationTokenDeleteListener(UUID userId){
        log.info("[MESSAGE BROKER] Received delete message for userId: " + userId);
        confirmationTokenService.deleteAllConfirmationTokensByUserId(userId);
    }
}

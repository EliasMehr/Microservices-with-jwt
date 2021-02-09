package com.advertisementproject.userservice.messagebroker.listener;

import com.advertisementproject.userservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of UserService.
 * When Confirmation Token Service has confirmed a token, it has the responsibility to inform this application that a
 * user should be enabled.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final UserService userService;

    /**
     * Listens for messages from Confirmation Token Service including a user id for the user that should be enabled,
     * then enabled the user for the supplied user id.
     *
     * @param userId the user id for which user should be enabled.
     */
    @RabbitListener(queues = "enableUser")
    public void enableUserListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received enableUser message for id: " + userId);
        userService.enableUser(userId);
    }
}

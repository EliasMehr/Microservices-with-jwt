package com.advertisementproject.permissionsservice.messagebroker.listener;

import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of PermissionsService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final PermissionsService permissionsService;

    /**
     * Listens for messages to add permissions to a specific user and then adds permissions for the user id supplied
     * @param userId the user id for which to grant permissions
     */
    @RabbitListener(queues = "#{permissionsAddQueue.name}")
    public void permissionsAddListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissionsAdd message for id: " + userId);
        permissionsService.createPermissions(userId);
    }

    /**
     * Listens for messages to remove permissions for a specific user and then removes permissions for the user id supplied
     * @param userId the user id for which to remove permissions
     */
    @RabbitListener(queues = "#{permissionsDeleteQueue.name}")
    public void permissionsDeleteListener(UUID userId){
        log.info("[MESSAGE BROKER] Received permissionsDelete message for id: " + userId);
        permissionsService.removePermissions(userId);
    }
}
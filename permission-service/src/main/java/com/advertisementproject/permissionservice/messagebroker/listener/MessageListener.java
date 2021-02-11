package com.advertisementproject.permissionservice.messagebroker.listener;

import com.advertisementproject.permissionservice.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of PermissionService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    /**
     * Service for managing CRUD operations for Permissions.
     */
    private final PermissionService permissionService;

    /**
     * Listens for messages to add permission to a specific user and then adds permission for the user id supplied
     *
     * @param userId the user id for which to grant permissions
     */
    @RabbitListener(queues = "#{permissionAddQueue.name}")
    public void permissionsAddListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissionsAdd message for id: " + userId);
        permissionService.createPermission(userId);
    }

    /**
     * Listens for messages to remove permission for a specific user and then removes permission for the user id supplied
     *
     * @param userId the user id for which to remove permission
     */
    @RabbitListener(queues = "#{permissionDeleteQueue.name}")
    public void permissionsDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissionsDelete message for id: " + userId);
        permissionService.removePermission(userId);
    }
}
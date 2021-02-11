package com.advertisementproject.zuulgateway.messagebroker.listener;

import com.advertisementproject.zuulgateway.db.entity.Permission;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionService;
import com.advertisementproject.zuulgateway.services.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of UserService and PermissionsService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    /**
     * Service for managing CRUD operations for users.
     */
    private final UserService userService;

    /**
     * Service for managing CRUD operations for permissions.
     */
    private final PermissionService permissionService;

    /**
     * Listens for messages from User Service application that a user should be added or updated
     *
     * @param messageObject the user to be created or updated, in JSON string format
     * @throws JsonProcessingException if messageObject cannot be read as a user object
     */
    @RabbitListener(queues = "#{userQueue.name}")
    public void userListener(String messageObject) throws JsonProcessingException {
        User user = new ObjectMapper().readValue(messageObject, User.class);
        log.info("[MESSAGE BROKER] Received user message: " + user);
        userService.saveOrUpdateUser(user);
    }

    /**
     * Listens for messages from User Service application that a user should be deleted
     *
     * @param userId the user id of the user to be deleted
     */
    @RabbitListener(queues = "#{userDeleteQueue.name}")
    public void userDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received user delete message for id: " + userId);
        userService.deleteUser(userId);
    }

    /**
     * Listens for messages from Permission Service application that a permission entity should be added or updated
     *
     * @param messageObject the permission to be created or updated, in JSON string format
     * @throws JsonProcessingException if messageObject cannot be read as a permission object
     */
    @RabbitListener(queues = "#{permissionQueue.name}")
    public void permissionsListener(String messageObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Permission permission = mapper.readValue(messageObject, Permission.class);
        log.info("[MESSAGE BROKER] Received permissions message: " + permission);
        permissionService.saveOrUpdatePermission(permission);
    }

    /**
     * Listens for messages from Permission Service application that a permissions entity should be deleted
     *
     * @param userId the user id of the permission entity to be deleted
     */
    @RabbitListener(queues = "#{permissionDeleteQueue.name}")
    public void permissionsDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissions delete message for id: " + userId);
        permissionService.deletePermission(userId);
    }
}

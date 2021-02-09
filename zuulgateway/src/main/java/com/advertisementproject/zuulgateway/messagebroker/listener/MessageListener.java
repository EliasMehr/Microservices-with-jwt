package com.advertisementproject.zuulgateway.messagebroker.listener;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionsService;
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

    private final UserService userService;
    private final PermissionsService permissionsService;

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
     * Listens for messages from Permissions Service application that a permissions entity should be added or updated
     *
     * @param messageObject the permissions to be created or updated, in JSON string format
     * @throws JsonProcessingException if messageObject cannot be read as a permissions object
     */
    @RabbitListener(queues = "#{permissionsQueue.name}")
    public void permissionsListener(String messageObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Permissions permissions = mapper.readValue(messageObject, Permissions.class);
        log.info("[MESSAGE BROKER] Received permissions message: " + permissions);
        permissionsService.saveOrUpdatePermissions(permissions);
    }

    /**
     * Listens for messages from Permissions Service application that a permissions entity should be deleted
     *
     * @param userId the user id of the permissions entity to be deleted
     */
    @RabbitListener(queues = "#{permissionsDeleteQueue.name}")
    public void permissionsDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissions delete message for id: " + userId);
        permissionsService.deletePermissions(userId);
    }
}

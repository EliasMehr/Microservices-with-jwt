package com.advertisementproject.permissionservice.messagebroker.publisher;

import com.advertisementproject.permissionservice.db.entity.Permission;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessagePublisher is a service for publishing messages asynchronously to other microservices via RabbitMQ.
 * RabbitTemplate is used to send the message and ObjectMapper is used to convert objects to JSON
 */
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Sends a permission object to a fanout exchange so that any microservice that needs to be updated about when
     * permission is created or updated can receive the update by having a queue connected to the exchange.
     *
     * @param permission the permissions object that has been created or updated
     */
    public void sendPermissionMessage(Permission permission) {
        try {
            String permissionsString = objectMapper.writeValueAsString(permission);
            rabbitTemplate.convertAndSend("permission", "", permissionsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a user id to a fanout exchange so that any microservice that needs to be updated about when
     * permission is removed for a user can receive the update by having a queue connected to the exchange.
     *
     * @param userId the id for the user who's permission has been removed
     */
    public void sendPermissionsDeleteMessage(UUID userId) {
        rabbitTemplate.convertAndSend("permission.delete", "", userId);
    }
}

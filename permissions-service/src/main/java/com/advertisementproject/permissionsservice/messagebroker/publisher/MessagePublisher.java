package com.advertisementproject.permissionsservice.messagebroker.publisher;

import com.advertisementproject.permissionsservice.db.entity.Permissions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendPermissionsMessage(Permissions permissions){
        try {
            String permissionsString = objectMapper.writeValueAsString(permissions);
            rabbitTemplate.convertAndSend("permissions", "", permissionsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendPermissionsDeleteMessage(UUID userId){
        rabbitTemplate.convertAndSend("permissions.delete", "", userId);
    }
}

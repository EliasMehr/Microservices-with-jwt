package com.advertisementproject.permissionsservice.messagebroker.listener;

import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final PermissionsService permissionsService;

    @RabbitListener(queues = "permissionsAdd")
    public void permissionsAddListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received permissionsAdd message for id: " + userId);
        permissionsService.createPermissions(userId);
    }

    @RabbitListener(queues = "permissionsDelete")
    public void permissionsDeleteListener(UUID userId){
        log.info("[MESSAGE BROKER] Received permissionsDelete message for id: " + userId);
        permissionsService.removePermissions(userId);
    }
}
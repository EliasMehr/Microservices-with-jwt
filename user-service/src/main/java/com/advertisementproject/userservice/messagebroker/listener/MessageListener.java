package com.advertisementproject.userservice.messagebroker.listener;

import com.advertisementproject.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final UserService userService;

    @RabbitListener(queues = "enableUser")
    public void enableUserListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received enableUser message for id: " + userId);
        userService.enableUser(userId);
    }
}

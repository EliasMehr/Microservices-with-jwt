package com.advertisementproject.zuulgateway.messagebroker.listener;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionsService;
import com.advertisementproject.zuulgateway.services.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageListener {

    private final UserService userService;
    private final PermissionsService permissionsService;

    @RabbitListener(queues = "#{userQueue.name}")
    public void userListener(String messageObject) throws JsonProcessingException {
        User user = new ObjectMapper().readValue(messageObject, User.class);
        userService.saveOrUpdateUser(user);
    }

    @RabbitListener(queues = "#{userDeleteQueue.name}")
    public void userDeleteListener(UUID userId){
        userService.deleteUser(userId);
    }


    @RabbitListener(queues = "#{permissionsQueue.name}")
    public void permissionsListener(String messageObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Permissions permissions = mapper.readValue(messageObject, Permissions.class);
        permissionsService.saveOrUpdatePermissions(permissions);
    }

    @RabbitListener(queues = "#{permissionsDeleteQueue.name}")
    public void permissionsDeleteListener(UUID userId){
        permissionsService.deletePermissions(userId);
    }
}

package com.advertisementproject.userservice.messagebroker.publisher;

import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.User;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendUserDeleteMessage(UUID userId) {
        rabbitTemplate.convertAndSend("user.delete", "", userId);
    }

    public void sendUserIdMessage(String queueName, UUID userId) {
        log.info("[MESSAGE BROKER] Sending userId to " + queueName + ": " + userId);
        rabbitTemplate.convertAndSend(queueName, userId);
    }

    public void sendEmailDetailsMessage(EmailDetailsMessage message) {
        String queueName = "emailDetails";
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending message to " + queueName + ": " + messageString);
        rabbitTemplate.convertAndSend(queueName, messageString);
    }

    public void sendUserMessage(User user) {
        String exchangeName = "user";
        String companyString = null;
        try {
            companyString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + companyString);
        rabbitTemplate.convertAndSend(exchangeName, "", companyString);
    }

    public void sendCompanyMessage(Company company) {
        String exchangeName = "company";
        String companyString = null;
        try {
            companyString = new ObjectMapper().writeValueAsString(company);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + companyString);
        rabbitTemplate.convertAndSend(exchangeName, "", companyString);
    }
}

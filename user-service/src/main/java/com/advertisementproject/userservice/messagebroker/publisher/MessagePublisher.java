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

/**
 * MessagePublisher is a service for publishing messages asynchronously to other microservices via RabbitMQ.
 * RabbitTemplate is used to send the message and ObjectMapper is used to convert objects to JSON
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    /**
     * Used for sending messages to other microservices via RabbitMQ message broker.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Sends a message including a user id to a fanout exchange with the name "user.delete" which informs other
     * microservices that they should delete any information related to that user id.
     *
     * @param userId the user id to be sent to "user.delete" fanout exchange
     */
    public void sendUserDeleteMessage(UUID userId) {
        rabbitTemplate.convertAndSend("user.delete", "", userId);
    }

    /**
     * Sends a message including a user id to a direct messaging queue with the supplied name which informs another
     * microservice that they should do something related to that user id.
     *
     * @param queueName the name of the queue to send a user id to
     * @param userId    the user id to be sent
     */
    public void sendUserIdMessage(String queueName, UUID userId) {
        log.info("[MESSAGE BROKER] Sending userId to " + queueName + ": " + userId);
        rabbitTemplate.convertAndSend(queueName, userId);
    }

    /**
     * Sends a message including email details to a direct messaging queue "emailDetails" which informs the Email
     * Service application that an email should be sent using the email details in the message.
     *
     * @param message email details message to be sent to Email Service application.
     */
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

    /**
     * Sends a message including a user object to a fanout exchange with the name "user" which informs other
     * microservices that a user has been added/updated and they should update their copy of the user table.
     *
     * @param user the user to be sent to "user" fanout exchange
     */
    public void sendUserMessage(User user) {
        String exchangeName = "user";
        String companyString = null;
        try {
            companyString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending user info to exchange " + exchangeName + ": " + companyString);
        rabbitTemplate.convertAndSend(exchangeName, "", companyString);
    }

    /**
     * Sends a message including a company object to a fanout exchange with the name "company" which informs other
     * microservices that a company has been added/updated and they should update their copy of the company table.
     *
     * @param company the company to be sent to "company" fanout exchange
     */
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

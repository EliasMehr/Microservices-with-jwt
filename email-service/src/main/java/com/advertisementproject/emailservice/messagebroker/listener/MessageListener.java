package com.advertisementproject.emailservice.messagebroker.listener;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.emailservice.service.interfaces.EmailDetailsService;
import com.advertisementproject.emailservice.service.interfaces.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MessageListener is a service that listens for messages from other microservices via RabbitMQ and then performs
 * appropriate actions for the messages received. When a message is sent to the listed queue, the message is received,
 * logged and handled in the listener method with the help of EmailDetailsService. If all information has been received,
 * EmailService sends out a confirmation link email and then deletes the database row for that user id.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    /**
     * Service for managing CRUD operations for EmailDetails.
     */
    private final EmailDetailsService emailDetailsService;

    /**
     * Service for sending confirmation link emails. Settings are configured in application.yml.
     */
    private final EmailService emailService;

    /**
     * Listens on the queue "emailDetails" which is preconfigured by User Service application that is responsible for
     * publishing a email details message once a user is created. Email details are saved to the database once received,
     * either as a new entry or added to the same user id if token already has been added for that user id.
     * If all information has been received, EmailService sends out a confirmation link email and then deletes the
     * database row for that user id.
     *
     * @param messageObject JSON string including an EmailDetailsMessage object from User Service application
     */
    @RabbitListener(queues = "emailDetails")
    public synchronized void emailDetailsListener(String messageObject) {
        try {
            EmailDetailsMessage emailDetailsMessage = new ObjectMapper().readValue(messageObject, EmailDetailsMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + emailDetailsMessage);

            emailDetailsService.saveDetails(emailDetailsMessage);
            sendEmailAndDeletePostIfCompleteInformation(emailDetailsMessage.getUserId());
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }

    /**
     * Listens on the queue "emailToken" which is preconfigured by Confirmation Token Service application that is
     * responsible for publishing a token message once a confirmation token is created. Token is saved to the database
     * once received, either as a new entry or added to the same user id if other email details already has been added
     * for that user id. If all information has been received, EmailService sends out a confirmation link email and then
     * deletes the database row for that user id.
     *
     * @param messageObject JSON string including a TokenMessage object from Confirmation Token Service application
     */
    @RabbitListener(queues = "emailToken")
    public synchronized void emailTokenListener(String messageObject) {
        try {
            TokenMessage tokenMessage = new ObjectMapper().readValue(messageObject, TokenMessage.class);
            log.info("[MESSAGE BROKER] Received message: " + tokenMessage);
            emailDetailsService.saveToken(tokenMessage);
            sendEmailAndDeletePostIfCompleteInformation(tokenMessage.getUserId());
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
        }
    }

    /**
     * Helper method to send an email if all the required information is available for a user id and in that case also
     * delete email details object for that user id once the email has been sent.
     *
     * @param userId the user id for which get email details and send email if all the information is present
     */
    private void sendEmailAndDeletePostIfCompleteInformation(UUID userId) {
        EmailDetails emailDetails = emailDetailsService.getCompleteDetailsOrNull(userId);
        if (emailDetails != null) {
            emailService.sendConfirmationLinkEmail(emailDetails.getEmail(), emailDetails.getName(), emailDetails.getToken());
            emailDetailsService.deleteEmailDetails(emailDetails);
        }
    }
}

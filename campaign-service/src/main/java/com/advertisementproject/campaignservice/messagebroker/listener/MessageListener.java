package com.advertisementproject.campaignservice.messagebroker.listener;

import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.service.interfaces.CompanyService;
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
 * logged and handled in the listener method with the help of CompanyService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    /**
     * Service for managing CRUD operations for Companies.
     */
    private final CompanyService companyService;

    /**
     * Listens for messages to remove campaigns for a specific company and removes campaigns for the company id supplied
     *
     * @param userId the user id (same as company id) for which to remove campaigns
     */
    @RabbitListener(queues = "#{deleteQueue.name}")
    public void userDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        companyService.deleteCompanyById(userId);
    }

    /**
     * Listens for messages including a company to add/update in the database and adds/updates the company in the
     * database to keep an up-to-date copy of the company table in the User Service
     *
     * @param messageObject JSON string including a company, which is then parsed to a company and added/updated
     * @throws JsonProcessingException exception is thrown if the company information in the JSON string cannot be parsed.
     */
    @RabbitListener(queues = "#{companyQueue.name}")
    public void companyChangeListener(String messageObject) throws JsonProcessingException {
        Company company = new ObjectMapper().readValue(messageObject, Company.class);
        companyService.saveOrUpdateCompany(company);
    }
}
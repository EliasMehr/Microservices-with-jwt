package com.advertisementproject.campaignservice.messagebroker.listener;

import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import com.advertisementproject.campaignservice.service.interfaces.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final CompanyService companyService;
    private final CampaignService campaignService;

    @RabbitListener(queues = "#{deleteQueue.name}")
    public void userDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        companyService.deleteCompanyById(userId);
    }

    @RabbitListener(queues = "#{companyQueue.name}")
    public void companyChangeListener(String messageObject) throws JsonProcessingException {
        Company company = new ObjectMapper().readValue(messageObject, Company.class);
        companyService.saveOrUpdateCompany(company);
    }
}
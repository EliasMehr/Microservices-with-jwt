package com.advertisementproject.campaignservice.messagebroker.listener;

import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final CampaignService campaignService;

    @RabbitListener(queues = "campaignsDelete")
    public void campaignsDeleteListener(UUID userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        campaignService.deleteAllCampaignsByCompanyId(userId);
    }
}
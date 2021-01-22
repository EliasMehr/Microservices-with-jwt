package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.repository.CampaignRepository;
import com.advertisementproject.campaignservice.service.interfaces.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class PublisherServiceImpl implements PublisherService {

    private final CampaignRepository campaignRepository;

    @Override
    @Scheduled(cron = "0 */10 * * * *")
    public void publishScheduledCampaigns() {
        log.info("Publishing scheduled campaigns");
        campaignRepository.publishScheduledCampaigns(Instant.now());
    }
}

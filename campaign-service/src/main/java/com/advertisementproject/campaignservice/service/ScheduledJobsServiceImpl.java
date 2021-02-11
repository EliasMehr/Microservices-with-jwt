package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.repository.CampaignRepository;
import com.advertisementproject.campaignservice.service.interfaces.ScheduledJobsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service implementation for running scheduled jobs to keep the database in desired state and removing unwanted data.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ScheduledJobsServiceImpl implements ScheduledJobsService {

    /**
     * JPA Repository for Campaigns.
     */
    private final CampaignRepository campaignRepository;

    /**
     * Runs scheduled jobs every 10 minutes
     */
    @Scheduled(cron = "0 */10 * * * *")
    @Override
    public void runScheduledJobs() {
        log.info("Running scheduled jobs");
        publishScheduledCampaigns();
        removeExpiredCampaigns();
    }

    /**
     * Publishes scheduled campaigns and logs the number of campaigns that were published
     */
    @Override
    public void publishScheduledCampaigns() {
        int nrOfPublishedCampaigns = campaignRepository.publishScheduledCampaigns(Instant.now());
        log.info("[SCHEDULED] Published " + nrOfPublishedCampaigns + " scheduled campaign(s)");
    }

    /**
     * Removes expired campaigns and logs the number of campaigns that were removed
     */
    @Override
    public void removeExpiredCampaigns() {
        int nrOfExpiredCampaigns = campaignRepository.removeExpiredCampaigns(Instant.now());
        log.info("[SCHEDULED] Removed " + nrOfExpiredCampaigns + " expired campaign(s)");
    }
}

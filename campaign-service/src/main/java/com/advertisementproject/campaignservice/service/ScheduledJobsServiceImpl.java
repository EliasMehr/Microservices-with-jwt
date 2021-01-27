package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.repository.CampaignRepository;
import com.advertisementproject.campaignservice.service.interfaces.ScheduledJobsService;
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
public class ScheduledJobsServiceImpl implements ScheduledJobsService {

    private final CampaignRepository campaignRepository;

    @Scheduled(cron = "0 */10 * * * *")
    @Override
    public void runScheduledJobs(){
        log.info("Running scheduled jobs");
        publishScheduledCampaigns();
        removeExpiredCampaigns();
    }

    @Override
    public void publishScheduledCampaigns() {
        int nrOfPublishedCampaigns = campaignRepository.publishScheduledCampaigns(Instant.now());
        log.info("[SCHEDULED] Published " + nrOfPublishedCampaigns + " scheduled campaign(s)");
    }

    @Override
    public void removeExpiredCampaigns() {
        int nrOfExpiredCampaigns = campaignRepository.removeExpiredCampaigns(Instant.now());
        log.info("[SCHEDULED] Removed " + nrOfExpiredCampaigns + " expired campaign(s)");
    }


}

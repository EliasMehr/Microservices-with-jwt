package com.advertisementproject.campaignservice.service.interfaces;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledJobsService {

    @Scheduled
    void runScheduledJobs();
    void publishScheduledCampaigns();
    void removeExpiredCampaigns();
}

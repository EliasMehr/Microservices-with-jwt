package com.advertisementproject.campaignservice.service.interfaces;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Service for running scheduled jobs to keep the database in desired state and removing unwanted data
 */
public interface ScheduledJobsService {

    /**
     * Runs the scheduled jobs according to a cron job timer
     */
    @Scheduled
    void runScheduledJobs();

    /**
     * Publishes scheduled campaigns
     */
    void publishScheduledCampaigns();

    /**
     * Removes expired campaigns
     */
    void removeExpiredCampaigns();
}

package com.advertisementproject.campaignservice.db.repository;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository for managing the campaign table in the database. Includes some custom methods beyond typical CRUD.
 */
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    /**
     * Retrieve all campaigns from the database for a specific company id
     *
     * @param companyId the company id to retrieve campaigns from
     * @return list of campaigns with the supplied company id
     */
    List<Campaign> findAllByCompanyUserId(UUID companyId);

    /**
     * Deletes all campaigns with the supplied company id
     *
     * @param companyId the company id for which to delete campaigns
     */
    void deleteByCompanyUserId(UUID companyId);

    /**
     * Retrieve all campaigns that are published, in other words where isPublished == true
     *
     * @return list of all published campaigns
     */
    List<Campaign> findAllByIsPublishedTrue();

    /**
     * Sets isPublished = true for campaigns that have a publishedAt timestamp in the past and that are not already set
     * to published. This is run as a scheduled job in ScheduledJobsService.
     *
     * @param instant a timestamp for which to compare the publishedAt timestamp. Normally set to Instant.now()
     * @return the number of campaigns that were set to published
     */
    @Transactional
    @Modifying
    @Query("UPDATE Campaign campaign SET campaign.isPublished = true WHERE campaign.isPublished = false AND campaign.publishedAt < ?1")
    int publishScheduledCampaigns(Instant instant);

    /**
     * Removes all campaigns that have a expiredAt timestamp in the past. This is run as a scheduled job in
     * ScheduledJobsService.
     *
     * @param instant a timestamp for which to compare the expiredAt timestamp. Normally set to Instant.now()
     * @return the number of expired campaigns that were removed
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Campaign campaign WHERE campaign.expiresAt < ?1")
    int removeExpiredCampaigns(Instant instant);

}

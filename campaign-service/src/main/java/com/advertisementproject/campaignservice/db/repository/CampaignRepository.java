package com.advertisementproject.campaignservice.db.repository;

import com.advertisementproject.campaignservice.db.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    List<Campaign> findAllByCompanyId(UUID companyId);
    void deleteByCompanyId(UUID companyId);
    List<Campaign> findAllByIsPublishedTrue();

    @Transactional
    @Modifying
    @Query("UPDATE Campaign campaign SET campaign.isPublished = true WHERE campaign.isPublished = false AND campaign.publishedAt < ?1")
    int publishScheduledCampaigns(Instant instant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Campaign campaign WHERE campaign.expiresAt < ?1")
    int removeExpiredCampaigns(Instant instant);
}

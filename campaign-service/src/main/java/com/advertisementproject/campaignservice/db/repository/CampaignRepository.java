package com.advertisementproject.campaignservice.db.repository;

import com.advertisementproject.campaignservice.db.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
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

//    SELECT CAST(id as varchar), CAST(company_id as varchar), title, description, discount, currency, image, category, created_At, published_At, expires_At, updated_At
//, discount_Code, is_Percentage, is_Published from Campaign INNER JOIN Company ON (Campaign.company_id = Company.user_id)
    @Transactional
    @Query(value = "SELECT  CAST(id as varchar), CAST(company_id as varchar), title, Campaign.description, discount, currency, image, category, created_At, published_At, expires_At, updated_At" +
            ", discount_Code, is_Percentage, is_Published, Company.name as company_Name from Campaign INNER JOIN Company ON (Campaign.company_id = Company.user_id)" +
            "WHERE campaign.is_Published = true", nativeQuery = true)
    List<Map<String, Object>> campaignsWithCompanyName();
}

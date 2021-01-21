package com.advertisementproject.campaignservice.db.repository;

import com.advertisementproject.campaignservice.db.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    List<Campaign> findAllByCompanyId(UUID companyId);
    void deleteByCompanyId(UUID companyId);
}
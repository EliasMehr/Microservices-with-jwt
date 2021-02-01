package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.request.CampaignRequest;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    List<Campaign> getAllCampaigns();

    List<Campaign> getAllPublishedCampaigns();

    List<Campaign> getAllCampaignsByCompanyId(UUID companyId);
    void deleteAllCampaignsByCompanyId(UUID companyId);

    Campaign getCampaignById(UUID campaignId, UUID companyId);

    Campaign createCampaign(Company company, CampaignRequest campaignRequest);

    Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest);
    void deleteCampaignById(UUID campaignId, UUID companyId);
    String getDiscountCode(UUID campaignId);

}

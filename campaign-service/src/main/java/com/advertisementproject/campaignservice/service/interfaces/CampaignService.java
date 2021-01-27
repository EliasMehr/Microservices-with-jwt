package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.request.CampaignRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CampaignService {

    List<Campaign> getAllCampaigns();
    List<Map<String, Object>> getAllPublishedCampaigns();
    List<Campaign> getAllCampaignsByCompanyId(UUID companyId);
    void deleteAllCampaignsByCompanyId(UUID companyId);
    Campaign getCampaignById(UUID campaignId, UUID companyId);
    Campaign createCampaign(UUID companyId, CampaignRequest campaignRequest);
    Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest);
    void deleteCampaignById(UUID campaignId, UUID companyId);
    String getDiscountCode(UUID campaignId);

}

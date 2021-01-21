package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.request.CampaignRequest;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    List<Campaign> getAllCampaigns();
    List<Campaign> getAllCampaignsByCompanyId(UUID companyId);
    void deleteAllCampaignsByCompanyId(UUID companyId);
    Campaign getCampaignById(UUID campaignId);
    Campaign createCampaign(UUID companyId, CampaignRequest campaignRequest);
    Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest);
    void deleteCampaignById(UUID campaignId, UUID companyId);


}

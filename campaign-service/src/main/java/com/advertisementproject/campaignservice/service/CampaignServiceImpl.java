package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CampaignServiceImpl implements CampaignService {


    @Override
    public List<Campaign> getAllCampaignsByCompanyId(UUID companyId) {
        return null;
    }

    @Override
    public void deleteAllCampaignsByCompanyId(UUID companyId) {

    }

    @Override
    public Campaign getCampaignById(UUID campaignId) {
        return null;
    }

    @Override
    public Campaign createCampaign(UUID companyId, CampaignRequest campaignRequest) {


        return null;
    }

    @Override
    public Campaign updateCampaignById(UUID campaignId, CampaignRequest campaignRequest) {
        return null;
    }

    @Override
    public void deleteCampaignById(UUID campaignId) {

    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return null;
    }
}

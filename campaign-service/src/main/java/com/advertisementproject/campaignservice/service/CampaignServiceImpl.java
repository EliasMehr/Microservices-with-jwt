package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.db.repository.CampaignRepository;
import com.advertisementproject.campaignservice.exception.CampaignNotFoundException;
import com.advertisementproject.campaignservice.exception.UnauthorizedAccessException;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import com.advertisementproject.campaignservice.service.interfaces.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final ValidationService validationService;

    @Override
    public List<Campaign> getAllCampaignsByCompanyId(UUID companyId) {
        return campaignRepository.findAllByCompanyId(companyId);
    }

    @Override
    @Transactional
    public void deleteAllCampaignsByCompanyId(UUID companyId) {
        campaignRepository.deleteByCompanyId(companyId);
    }

    @Override
    public Campaign getCampaignById(UUID campaignId, UUID companyId) {
        return getCampaignAndAuthorize(campaignId, companyId);
    }

    @Override
    public Campaign createCampaign(UUID companyId, CampaignRequest campaignRequest) {
        Campaign campaign = Campaign.toCampaign(companyId, campaignRequest);
        validationService.validateCampaign(campaign);
        campaignRepository.save(campaign);

        return campaign;
    }

    @Override
    public Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest) {
        Campaign campaign = getCampaignAndAuthorize(campaignId, companyId);

        updateCampaignFields(campaign, campaignRequest);

        validationService.validateCampaign(campaign);
        campaignRepository.save(campaign);
        return campaign;
    }

    @Override
    public void deleteCampaignById(UUID campaignId, UUID companyId) {
        Campaign campaign = getCampaignAndAuthorize(campaignId, companyId);
        campaignRepository.delete(campaign);
    }


    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public List<Campaign> getAllPublishedCampaigns() {
        return campaignRepository.findAllByIsPublishedTrue();
    }


    private Campaign getCampaignAndAuthorize(UUID campaignId, UUID companyId) {
        Campaign campaign = getCampaignById(campaignId, companyId);
        if(!campaign.getCompanyId().equals(companyId)){
            throw new UnauthorizedAccessException("Unauthorized access not allowed");
        }
        return campaign;
    }

    private void updateCampaignFields(Campaign campaign, CampaignRequest request) {
        if(request.getTitle() != null){
            campaign.setTitle(request.getTitle());
        }
        if(request.getDescription() != null){
            campaign.setDescription(request.getDescription());
        }
        if(request.getDiscount() != null){
            campaign.setDiscount(request.getDiscount());
        }
        if(request.getIsPercentage() != null){
            campaign.setPercentage(request.getIsPercentage());
        }
        if(request.getImage() != null){
            campaign.setImage(request.getImage());
        }
        if(request.getCategory() != null){
            campaign.setCategory(request.getCategory());
        }
        if(!campaign.isPublished() && request.isPublishNow()){
            campaign.setPublished(true);
            campaign.setPublishedAt(Instant.now());
        }
        if (request.getPublishedAt() != null && request.getPublishedAt().isAfter(Instant.now().plus(Period.ofDays(1)))){
            campaign.setPublished(false);
            campaign.setPublishedAt(request.getPublishedAt());
        }
        if(request.getExpiresAt() != null && request.getExpiresAt().isAfter(campaign.getPublishedAt())){
            campaign.setExpiresAt(request.getExpiresAt());
        }
        if(request.getDiscountCode() != null){
            campaign.setDiscountCode(request.getDiscountCode());
        }
        campaign.setUpdatedAt(Instant.now());
    }
}

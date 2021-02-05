package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.db.repository.CampaignRepository;
import com.advertisementproject.campaignservice.exception.EntityNotFoundException;
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

/**
 * Service implementation for Campaign Service to manage campaigns
 */
@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final ValidationService validationService;

    /**
     * Retrieves all campaigns for a specific company id
     * @param companyId the company id for which to retrieve campaigns
     * @return list of companies related to the supplied company id
     */
    @Override
    public List<Campaign> getAllCampaignsByCompanyId(UUID companyId) {
        return campaignRepository.findAllByCompanyUserId(companyId);
    }

    /**
     * Deletes all campaigns for a specific company id
     * @param companyId the company id for which to delete campaigns
     */
    @Override
    @Transactional
    public void deleteAllCampaignsByCompanyId(UUID companyId) {
        campaignRepository.deleteByCompanyUserId(companyId);
    }

    /**
     * Retrieves a specific campaign
     * @param campaignId the id of the campaign to retrieve
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     *                  campaign in question
     * @return the requested campaign
     */
    @Override
    public Campaign getCampaignById(UUID campaignId, UUID companyId) {
        return getCampaignAndAuthorize(campaignId, companyId);
    }

    /**
     * Validates and creates a new campaign
     * @param company the company that the campaign belongs to
     * @param campaignRequest information from which to generate the new campaign
     * @return the newly created campaign
     */
    @Override
    public Campaign createCampaign(Company company, CampaignRequest campaignRequest) {
        Campaign campaign = Campaign.toCampaign(company, campaignRequest);
        validationService.validateCampaign(campaign);
        campaignRepository.save(campaign);
        return campaign;
    }

    /**
     * Validates and updates a specific campaign
     * @param campaignId the id of the campaign to be updated
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     *                  campaign in question
     * @param campaignRequest information from which to update the campaign
     * @return the newly updated campaign
     */
    @Override
    public Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest) {
        Campaign campaign = getCampaignAndAuthorize(campaignId, companyId);

        updateCampaignFields(campaign, campaignRequest);

        validationService.validateCampaign(campaign);
        campaignRepository.save(campaign);
        return campaign;
    }

    /**
     * Deletes a specific campaign
     * @param campaignId the id of the campaign to delete
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     */
    @Override
    public void deleteCampaignById(UUID campaignId, UUID companyId) {
        Campaign campaign = getCampaignAndAuthorize(campaignId, companyId);
        campaignRepository.delete(campaign);
    }

    /**
     * Retrieves the discount code for a specific campaign
     * @param campaignId the id of the campaign to get the discount code from
     * @return the discount code for the campaign with the supplied campaign id
     * @throws EntityNotFoundException if the campaign is not found
     * @throws UnauthorizedAccessException if the campaign is not published for the requested discount code
     */
    @Override
    public String getDiscountCode(UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found for id: " + campaignId));
        if(!campaign.isPublished()){
            throw new UnauthorizedAccessException("Access to discountCode for unpublished campaigns is not allowed");
        }
        return campaign.getDiscountCode();
    }

    /**
     * Retrieves all campaigns
     * @return list of all campaigns
     */
    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    /**
     * Retrieves all published campaigns
     * @return list of all published campaigns
     */
    @Override
    public List<Campaign> getAllPublishedCampaigns() {
        return campaignRepository.findAllByIsPublishedTrue();
    }


    /**
     * Helper method to retrieve a campaign and verify that the supplied company id matches the company id of the
     * requested campaign.
     * @param campaignId the id of the campaign to retrieve
     * @param companyId the company id to verify ownership
     * @return the requested campaign
     * @throws EntityNotFoundException if the campaign is not found.
     * @throws UnauthorizedAccessException if the supplied company id does not match the campaign's company id
     */
    private Campaign getCampaignAndAuthorize(UUID campaignId, UUID companyId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found for id: " + campaignId));
        if(!campaign.getCompany().getUserId().equals(companyId)){
            throw new UnauthorizedAccessException("Unauthorized access not allowed");
        }
        return campaign;
    }

    /**
     * Helper method to update a campaign. Updates not null fields in the supplied request.
     * Campaigns may be set to be published immediately or set to be published at a later time. The timestamp fields
     * publishedAt and expiredAt are validated here, but the other fields must still be validated.
     * @param campaign the campaign to update
     * @param request request object containing fields to update in the supplied campaign
     */
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

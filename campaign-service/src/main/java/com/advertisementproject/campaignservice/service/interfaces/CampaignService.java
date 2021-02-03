package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.request.CampaignRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing campaigns in the database with CRUD operations and enabling the controller endpoints to work.
 */
public interface CampaignService {

    /**
     * Retrieves all campaigns
     * @return list of all campaigns
     */
    List<Campaign> getAllCampaigns();

    /**
     * Retrieves all published campaigns
     * @return list of all campaigns that are published
     */
    List<Campaign> getAllPublishedCampaigns();

    /**
     * Retrieves all campaigns for a specific company id
     * @param companyId the company id for which to retrieve campaigns
     * @return list of all campaigns related to the supplied company id
     */
    List<Campaign> getAllCampaignsByCompanyId(UUID companyId);

    /**
     * Deletes all campaigns for a specific company id
     * @param companyId the company id for which to delete campaigns
     */
    void deleteAllCampaignsByCompanyId(UUID companyId);

    /**
     * Retrieves a specific campaign
     * @param campaignId the id of the campaign to retrieve
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     *                  campaign in question
     * @return the requested campaign
     */
    Campaign getCampaignById(UUID campaignId, UUID companyId);

    /**
     * Creates a new campaign and saves to database
     * @param company the company that the campaign belongs to
     * @param campaignRequest information from which to generate the new campaign
     * @return the newly created campaign
     */
    Campaign createCampaign(Company company, CampaignRequest campaignRequest);

    /**
     * Updates a campaign in the database
     * @param campaignId the id of the campaign to be updated
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     *                  campaign in question
     * @param campaignRequest information from which to update the campaign
     * @return the newly updated campaign
     */
    Campaign updateCampaignById(UUID campaignId, UUID companyId, CampaignRequest campaignRequest);

    /**
     * Deletes a specific campaign
     * @param campaignId the id of the campaign to delete
     * @param companyId the company id for the campaign. Used to verify that the company client has ownership of the
     *                  campaign in question
     */
    void deleteCampaignById(UUID campaignId, UUID companyId);

    /**
     * Retrieves the discount code for a specific campaign
     * @param campaignId the id of the campaign to get the discount code from
     * @return the discount code from the campaign with the supplied campaign id
     */
    String getDiscountCode(UUID campaignId);

}

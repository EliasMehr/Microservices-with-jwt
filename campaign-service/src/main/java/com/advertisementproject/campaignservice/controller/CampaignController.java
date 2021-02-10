package com.advertisementproject.campaignservice.controller;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.db.entity.view.View;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import com.advertisementproject.campaignservice.service.interfaces.CompanyService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Controller for campaigns management. Uses CampaignService and CompanyService to fulfill the requests. Different
 * endpoints have different access restrictions defined by the Zuul Gateway application.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    private final CompanyService companyService;

    /**
     * Endpoint for getting all campaigns for all companies. Should only be available for ADMIN users.
     *
     * @return Response entity with a list of all campaigns in the database.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    /**
     * Endpoint for getting all published campaigns with limited information. Should be open to all users.
     *
     * @return Response entity with a list of all published campaigns in the database with limited information.
     * @JsonView restricts the information so that discount codes and company ids are hidden in the response.
     */
    @GetMapping("/all/published")
    @JsonView(value = {View.publicInfo.class})
    public ResponseEntity<List<Campaign>> getAllPublishedCampaigns() {
        List<Campaign> campaigns = campaignService.getAllPublishedCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    /**
     * Endpoint for getting all campaigns for the currently logged in company.
     *
     * @param companyId the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity with a list of all campaigns and custom response headers for the logged in company in the database.
     */
    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@RequestHeader("userId") UUID companyId) {
        HttpHeaders customResponseHeader = new HttpHeaders();
        List<Campaign> campaigns = campaignService.getAllCampaignsByCompanyId(companyId);
        customResponseHeader.set("X-Total-Count", String.valueOf(campaigns.size()));
        customResponseHeader.set("Access-Control-Expose-Headers", "Content-Range");
        return ResponseEntity.ok()
                .headers(customResponseHeader)
                .body(campaigns);
    }

    /**
     * Endpoint for deleting all campaigns for the currently logged in company.
     *
     * @param companyId the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity with the message that all campaigns have been deleted for the companyId provided.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@RequestHeader("userId") UUID companyId) {
        campaignService.deleteAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    /**
     * Endpoint for a company to create a new campaign.
     *
     * @param campaignRequest campaign request object that allows the client to specify what information the campaign
     *                        should include. Some fields are optional and some are validated for not being null.
     * @param companyId       the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity returning the newly created campaign which has been saved in the database.
     */
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody CampaignRequest campaignRequest,
                                                   @RequestHeader("userId") UUID companyId) {
        Company company = companyService.getCompanyById(companyId);
        Campaign campaign = campaignService.createCampaign(company, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    /**
     * Endpoint for getting a specific campaign.
     *
     * @param campaignId the id for the requested campaign.
     * @param companyId  the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity with the campaign that was requested.
     */
    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId,
                                                @RequestHeader("userId") UUID companyId) {
        Campaign campaign = campaignService.getCampaignById(campaignId, companyId);
        return ResponseEntity.ok(campaign);
    }

    /**
     * Endpoint for updating a specific campaign.
     *
     * @param campaignId      the id of the campaign to be updated.
     * @param campaignRequest request object containing the fields that should be changed. Only the provided fields will
     *                        validated and then changed if valid.
     * @param companyId       the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity with the updated campaign.
     */
    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId,
                                                   @RequestBody CampaignRequest campaignRequest,
                                                   @RequestHeader("userId") UUID companyId) {
        Campaign campaign = campaignService.updateCampaignById(campaignId, companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    /**
     * Endpoint for deleting a specific campaign.
     *
     * @param campaignId the id of the campaign to be deleted.
     * @param companyId  the id for the currently logged in company user, provided by Zuul Gateway in the header.
     * @return Response entity with the message that the specific campaign has been deleted.
     */
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId,
                                                 @RequestHeader("userId") UUID companyId) {
        campaignService.deleteCampaignById(campaignId, companyId);
        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }

    /**
     * Endpoint for logged in users to retrieve the discount code for a specific campaign.
     *
     * @param campaignId the id of the campaign to retrieve the discount code from.
     * @return Response entity with the requested discount code.
     */
    @GetMapping("/discount-code/{campaignId}")
    public ResponseEntity<String> getDiscountCode(@PathVariable UUID campaignId) {
        String discountCode = campaignService.getDiscountCode(campaignId);
        return ResponseEntity.ok(discountCode);
    }
}

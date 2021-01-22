package com.advertisementproject.campaignservice.controller;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.exception.CampaignNotFoundException;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> getAllCampaigns(){
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/all/published")
    public ResponseEntity<List<Campaign>> getAllPublishedCampaigns(){
        List<Campaign> campaigns = campaignService.getAllPublishedCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/all/company/{companyId}")
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@PathVariable UUID companyId){
        List<Campaign> campaigns = campaignService.getAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok(campaigns);
    }

    @DeleteMapping("/all/company/{companyId}")
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@PathVariable UUID companyId){
        campaignService.deleteAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    @PostMapping("/company/{companyId}")
    public ResponseEntity<Campaign> createCampaign(@PathVariable UUID companyId,
                                                   @Valid @RequestBody CampaignRequest campaignRequest) {

        Campaign campaign = campaignService.createCampaign(companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/{campaignId}/company/{companyId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId, @PathVariable UUID companyId){
        Campaign campaign = campaignService.getCampaignById(campaignId, companyId);
        return ResponseEntity.ok(campaign);
    }

    @PutMapping("/{campaignId}/company/{companyId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId,
                                                   @PathVariable UUID companyId,
                                                   @RequestBody CampaignRequest campaignRequest){

        Campaign campaign = campaignService.updateCampaignById(campaignId, companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @DeleteMapping("/{campaignId}/company/{companyId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId, @PathVariable UUID companyId){
        campaignService.deleteCampaignById(campaignId, companyId);
        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }
}

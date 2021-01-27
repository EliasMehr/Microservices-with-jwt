package com.advertisementproject.campaignservice.controller;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.advertisementproject.campaignservice.service.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
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
    public ResponseEntity<List<Map<String, Object>>> getAllPublishedCampaigns(){
        List<Map<String, Object>> campaigns = campaignService.getAllPublishedCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@RequestHeader("userId") UUID companyId){
        List<Campaign> campaigns = campaignService.getAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok(campaigns);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@RequestHeader("userId") UUID companyId){
        campaignService.deleteAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody CampaignRequest campaignRequest,
                                                   @RequestHeader("userId") UUID companyId) {

        Campaign campaign = campaignService.createCampaign(companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId,
                                                @RequestHeader("userId") UUID companyId){
        Campaign campaign = campaignService.getCampaignById(campaignId, companyId);
        return ResponseEntity.ok(campaign);
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId,
                                                   @RequestBody CampaignRequest campaignRequest,
                                                   @RequestHeader("userId") UUID companyId) {
        Campaign campaign = campaignService.updateCampaignById(campaignId, companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @DeleteMapping("/{campaignId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId,
                                                 @RequestHeader("userId") UUID companyId){
        campaignService.deleteCampaignById(campaignId, companyId);
        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }
}

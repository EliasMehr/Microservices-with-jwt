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

@Slf4j
@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    private final CompanyService companyService;

    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> getAllCampaigns(){
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/all/published")
    @JsonView(value = {View.publicInfo.class})
    public ResponseEntity<List<Campaign>> getAllPublishedCampaigns() {
        List<Campaign> campaigns = campaignService.getAllPublishedCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@RequestHeader("userId") UUID companyId){
        HttpHeaders customResponseHeader = new HttpHeaders();
        List<Campaign> campaigns = campaignService.getAllCampaignsByCompanyId(companyId);
        customResponseHeader.set("X-Total-Count", String.valueOf(campaigns.size()));
        customResponseHeader.set("Access-Control-Expose-Headers", "Content-Range");
        return ResponseEntity.ok()
                .headers(customResponseHeader)
                .body(campaigns);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@RequestHeader("userId") UUID companyId){
        campaignService.deleteAllCampaignsByCompanyId(companyId);
        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody CampaignRequest campaignRequest,
                                                   @RequestHeader("userId") UUID companyId) {
        Company company = companyService.getCompanyById(companyId);
        Campaign campaign = campaignService.createCampaign(company, campaignRequest);
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


    @GetMapping("/discount-code/{campaignId}")
    public ResponseEntity<String> getDiscountCode(@PathVariable UUID campaignId){
        String discountCode = campaignService.getDiscountCode(campaignId);
        return ResponseEntity.ok(discountCode);
    }
}

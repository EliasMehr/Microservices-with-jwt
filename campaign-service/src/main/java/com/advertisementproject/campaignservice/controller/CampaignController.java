package com.advertisementproject.campaignservice.controller;

import com.advertisementproject.campaignservice.db.model.Campaign;
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


        return null;
    }

    @GetMapping("/all/company/{companyId}")
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@PathVariable UUID companyId){



        return null;
    }

    @DeleteMapping("/all/company/{companyId}")
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@PathVariable UUID companyId){


        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    @PostMapping("/company/{companyId}")
    public ResponseEntity<Campaign> createCampaign(@PathVariable UUID companyId,
                                                   @Valid @RequestBody CampaignRequest campaignRequest) {

        Campaign campaign = campaignService.createCampaign(companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId){



        return null;
    }

    @PutMapping("/{campaignId}/company/{companyId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId,
                                                   @PathVariable String companyId,
                                                   @Valid @RequestBody CampaignRequest campaignRequest){

        return null;
    }

    @DeleteMapping("/{campaignId}/company/{companyId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId, @PathVariable String companyId){



        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }
}

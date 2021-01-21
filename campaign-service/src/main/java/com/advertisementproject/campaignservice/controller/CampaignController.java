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

    @GetMapping("/all/{companyId}")
    public ResponseEntity<List<Campaign>> getCampaignsByCompanyId(@PathVariable UUID companyId){



        return null;
    }

    @DeleteMapping("/all/{companyId}")
    public ResponseEntity<String> deleteAllCampaignsByCompanyId(@PathVariable UUID companyId){


        return ResponseEntity.ok("All campaigns have been deleted for id: " + companyId);
    }

    @PostMapping("/create/{companyId}")
    public ResponseEntity<Campaign> createCampaign(@PathVariable UUID companyId,
                                                   @Valid @RequestBody CampaignRequest campaignRequest) {

        Campaign campaign = campaignService.createCampaign(companyId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId){



        return null;
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId,
                                                   @Valid @RequestBody CampaignRequest campaignRequest){



        return null;
    }

    @DeleteMapping("/{campaignId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId){



        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }
}

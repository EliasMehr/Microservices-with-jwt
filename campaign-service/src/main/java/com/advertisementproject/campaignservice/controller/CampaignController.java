package com.advertisementproject.campaignservice.controller;

import com.advertisementproject.campaignservice.db.model.Campaign;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CampaignController {

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

    @PostMapping
    public ResponseEntity<Campaign> createCampaign() {


        return null;
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID campaignId){



        return null;
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable UUID campaignId){



        return null;
    }

    @DeleteMapping("/{campaignId}")
    public ResponseEntity<String> deleteCampaign(@PathVariable UUID campaignId){



        return ResponseEntity.ok("Campaign has been deleted for id: " + campaignId);
    }
}

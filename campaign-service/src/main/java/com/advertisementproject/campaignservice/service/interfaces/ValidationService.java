package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.entity.Campaign;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Validates Entities
 */
public interface ValidationService {

    /**
     * Validates that a campaign has all fields valid
     *
     * @param campaign the campaign to be validated
     */
    void validateCampaign(@Valid @RequestBody Campaign campaign);
}

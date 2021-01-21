package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.service.interfaces.ValidationService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateCampaign(@Valid @RequestBody Campaign campaign) {
    }
}

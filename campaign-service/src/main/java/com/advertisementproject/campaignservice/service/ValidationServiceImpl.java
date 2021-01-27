package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.model.Campaign;
import com.advertisementproject.campaignservice.service.interfaces.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.Instant;

@Service
@Validated
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateCampaign(@Valid @RequestBody Campaign campaign) {
    }
}

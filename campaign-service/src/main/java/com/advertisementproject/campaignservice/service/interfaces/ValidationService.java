package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.model.Campaign;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.Instant;

public interface ValidationService {

    void validateCampaign(@Valid @RequestBody Campaign campaign);
}

package com.advertisementproject.campaignservice.exception;

public class CampaignNotFoundException extends RuntimeException {

    public CampaignNotFoundException(String message) {
        super(message);
    }

}

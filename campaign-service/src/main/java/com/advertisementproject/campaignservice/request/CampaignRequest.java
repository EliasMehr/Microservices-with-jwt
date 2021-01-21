package com.advertisementproject.campaignservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class CampaignRequest {
    @NotNull
    private String title;
    private String description;
    @NotNull
    private double discount;
    @NotNull
    private boolean isPercentage;
    private byte[] image;
    @NotNull
    private String category;
    private Instant publishedAt;
    private Instant expiresAt;
    @NotNull
    private String discountCode;
}

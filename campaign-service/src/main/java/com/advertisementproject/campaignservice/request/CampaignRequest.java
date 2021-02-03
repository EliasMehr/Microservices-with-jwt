package com.advertisementproject.campaignservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Request object used both for adding new campaigns as well as updating existing campaigns. @NotNull field validation
 * is only applied when adding a new campaign.
 */
@Data
public class CampaignRequest {
    @NotNull
    private String title;
    private String description;
    @NotNull
    private Double discount;
    @NotNull
    private Boolean isPercentage;
    private byte[] image;
    @NotNull
    private String category;
    private Instant publishedAt;
    private Instant expiresAt;
    @NotNull
    private String discountCode;
    private boolean publishNow;
}

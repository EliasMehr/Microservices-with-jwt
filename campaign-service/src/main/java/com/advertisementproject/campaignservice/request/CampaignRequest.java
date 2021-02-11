package com.advertisementproject.campaignservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Request object used both for adding new campaigns as well as updating existing campaigns. @NotNull field validation
 * is only applied when adding a new campaign, so for an update request only the fields to be changed need to be
 * supplied.
 */
@Data
public class CampaignRequest {

    /**
     * Title of the campaign to create/update. Mandatory when creating a campaign.
     */
    @NotNull
    private String title;

    /**
     * Description for the campaign to create/update.
     */
    private String description;

    /**
     * Discount offered in the campaign to create/update. Mandatory when creating a campaign.
     */
    @NotNull
    private Double discount;

    /**
     * Whether the discount is a percentage (true) or a fixed amount (false) in the campaign to create/update.
     * Mandatory when creating a campaign.
     */
    @NotNull
    private Boolean isPercentage;

    /**
     * Image in base64 string format.
     */
    private String image;

    /**
     * Category for the campaign to create/update. Mandatory when creating a campaign.
     */
    @NotNull
    private String category;

    /**
     * Timestamp for when to publish the campaign. May be left null when creating a campaign in which case it is set
     * to the current timestamp.
     */
    private Instant publishedAt;

    /**
     * Timestamp for when the campaign expires. May be left null when creating a campaign in which case it is set
     * to 60 days after "publishAt".
     */
    private Instant expiresAt;

    /**
     * Discount code which a customer can user to claim the discount the campaign has to offer.
     * Mandatory when creating a campaign.
     */
    @NotNull
    private String discountCode;

    /**
     * Whether the campaign should be published right away. Only used in update requests to set "publishAt"
     * to current timestamp.
     */
    private boolean publishNow;
}

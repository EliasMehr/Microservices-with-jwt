package com.advertisementproject.campaignservice.db.entity;

import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.Period;
import java.util.Currency;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    @Size(min = 2, max = 20, message = "Title must be between 2-20 characters long")
    private String title;

    private String description;

    @NotNull
    @Min(value = 1, message = "Discount must be at least 1 (percent or in currency)")
    private double discount;

    private Currency currency;

    @NotNull
    @JsonProperty(value = "isPercentage")
    private boolean isPercentage;

    private byte[] image;

    @NotNull
    @Size(min = 2, max = 20, message = "Category must be between 2-20 characters long")
    private String category;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private Instant createdAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private Instant publishedAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private Instant expiresAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    @JsonProperty(value = "isPublished")
    private boolean isPublished;

    @NotNull
    @Size(min = 2, max = 20, message = "Discount code must be between 2-20 characters long")
    private String discountCode;

    @NotNull
    private UUID companyId; //TODO make sure that when a company user is deleted, the campaigns are also deleted or added to a legacy table

    public static Campaign toCampaign(UUID companyId, CampaignRequest request) {
        Instant publishedAt;
        boolean isPublished = false;
        if(request.getPublishedAt() != null && request.getPublishedAt().isAfter(Instant.now())){
            publishedAt = request.getPublishedAt();
        }
        else {
            publishedAt = Instant.now();
            isPublished = true;
        }
        Instant expiresAt = publishedAt.plus(Period.ofDays(60));
        if(request.getExpiresAt() != null && request.getExpiresAt().isAfter(publishedAt)){
            expiresAt = request.getExpiresAt();
        }
        return Campaign.builder()
                .id(UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .discount(request.getDiscount())
                .currency(Currency.getInstance("SEK"))
                .isPercentage(request.getIsPercentage())
                .image(request.getImage())
                .category(request.getCategory())
                .createdAt(Instant.now())
                .publishedAt(publishedAt)
                .isPublished(isPublished)
                .expiresAt(expiresAt)
                .updatedAt(null)
                .discountCode(request.getDiscountCode())
                .companyId(companyId)
                .build();
    }
}

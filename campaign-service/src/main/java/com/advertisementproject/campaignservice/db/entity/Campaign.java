package com.advertisementproject.campaignservice.db.entity;

import com.advertisementproject.campaignservice.db.entity.view.View;
import com.advertisementproject.campaignservice.request.CampaignRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    @JsonView(value = {View.publicInfo.class})
    private UUID id;

    @NotNull
    @Size(min = 2, max = 20, message = "Title must be between 2-20 characters long")
    @JsonView(value = {View.publicInfo.class})
    private String title;

    @JsonView(value = {View.publicInfo.class})
    private String description;

    @NotNull
    @Min(value = 1, message = "Discount must be at least 1 (percent or in currency)")
    @JsonView(value = {View.publicInfo.class})
    private double discount;

    @JsonView(value = {View.publicInfo.class})
    private Currency currency;

    @NotNull
    @JsonProperty(value = "isPercentage")
    @JsonView(value = {View.publicInfo.class})
    private boolean isPercentage;

    @JsonView(value = {View.publicInfo.class})
    private byte[] image;

    @NotNull
    @Size(min = 2, max = 20, message = "Category must be between 2-20 characters long")
    @JsonView(value = {View.publicInfo.class})
    private String category;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant createdAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant publishedAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant expiresAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @JsonView(value = {View.publicInfo.class})
    private Instant updatedAt;

    @JsonProperty(value = "isPublished")
    @JsonView(value = {View.publicInfo.class})
    private boolean isPublished;

    @NotNull
    @Size(min = 2, max = 20, message = "Discount code must be between 2-20 characters long")
    private String discountCode;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id")
    @JsonView(value = {View.publicInfo.class})
    private Company company;

    public static Campaign toCampaign(Company company, CampaignRequest request) {
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
                .company(company)
                .build();
    }
}

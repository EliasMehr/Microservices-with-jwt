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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.Period;
import java.util.Currency;
import java.util.UUID;

/**
 * Campaigns are the main responsibility of the Campaign Service application and contain information about the campaign
 * itself for advertising products or services to customers. Fields include validation.
 * Each campaign is tied to a company, which are updated when the application receives messages from the User Service
 * application. If a company is removed then all related campaigns are also removed.
 *
 * @JsonView restricts the response information shown to only the annotated fields if a controller has set a view.
 */
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

    @Lob
    @Column(columnDefinition = "text")
    @JsonView(value = {View.publicInfo.class})
    @Size(min = 1000, message = "Too short. Image must be in base64 string format")
    @Pattern(regexp = "^data:image/.*$", message = "image string must begin with 'data:image/' image descriptor")
    private String image;

    @NotNull
    @Size(min = 2, max = 20, message = "Category must be between 2-20 characters long")
    @JsonView(value = {View.publicInfo.class})
    private String category;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant publishedAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    @JsonView(value = {View.publicInfo.class})
    private Instant expiresAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
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

    /**
     * Builder method for constructing a campaign from a company and a campaign request.
     * If publishedAt timestamp is provided in the request and it's a future timestamp then the campaign is set to be
     * published at that later time, otherwise it will be set to published with the current timestamp.
     * If expiredAt timestamp is provided in the request and it's at a later timestamp than the publishAt timestamp
     * then that expiredAt timestamp is set, otherwise expiredAt is set to 60 days after publishAt by default.
     *
     * @param company the company that created the campaign
     * @param request request object containing information for making the campaign
     * @return campaign with information provided from the request, belonging to the company provided
     */
    public static Campaign toCampaign(Company company, CampaignRequest request) {
        Instant publishedAt;
        boolean isPublished = false;
        if (request.getPublishedAt() != null && request.getPublishedAt().isAfter(Instant.now())) {
            publishedAt = request.getPublishedAt();
        } else {
            publishedAt = Instant.now();
            isPublished = true;
        }
        Instant expiresAt = publishedAt.plus(Period.ofDays(60));
        if (request.getExpiresAt() != null && request.getExpiresAt().isAfter(publishedAt)) {
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

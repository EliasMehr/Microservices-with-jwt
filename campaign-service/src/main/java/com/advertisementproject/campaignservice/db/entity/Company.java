package com.advertisementproject.campaignservice.db.entity;

import com.advertisementproject.campaignservice.db.entity.type.CompanyType;
import com.advertisementproject.campaignservice.db.entity.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Company contains information about the company that created the campaign and each campaign must be tied to a company.
 * If a company is removed then all related campaigns are also removed. The User Service is responsible for informing
 * this application of any changes to the company database so that this application also may keep an up to date copy.
 *
 * @JsonView restricts the information in a JSON response to only the annotated fields if a view is set in the controller.
 */
@Data
@Entity
public class Company {

    /**
     * Primary id for Company entity that matches user id.
     */
    @Id
    private UUID userId;

    /**
     * The name of the company.
     */
    @JsonView(value = {View.publicInfo.class})
    private String name;

    /**
     * The organization number for the company, which is like an id number for companies in the Swedish system.
     */
    @JsonView(value = {View.publicInfo.class})
    private String organizationNumber;

    /**
     * Optional description of the company.
     */
    @JsonView(value = {View.publicInfo.class})
    private String description;

    /**
     * The type of company that the company can be classified as, for example "RETAIL" or "SOFTWARE".
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonView(value = {View.publicInfo.class})
    private CompanyType companyType;

}

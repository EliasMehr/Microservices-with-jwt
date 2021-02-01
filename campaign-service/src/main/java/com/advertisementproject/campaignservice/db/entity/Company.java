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

@Data
@Entity
public class Company {

    @Id
    private UUID userId;

    @JsonView(value = {View.publicInfo.class})
    private String name;

    @JsonView(value = {View.publicInfo.class})
    private String organizationNumber;

    @JsonView(value = {View.publicInfo.class})
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonView(value = {View.publicInfo.class})
    private CompanyType companyType;

}

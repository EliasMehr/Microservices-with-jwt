package com.advertisementproject.userservice.db.entity;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.db.entity.types.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Company entity for company users with information about the company the user has
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @Id
    private UUID userId;
    @NotNull
    @Size(min = 2, max = 30, message = "Name must be 2-30 characters long")
    private String name;
    @NotNull(message = "Organization number must not be null")
    private String organizationNumber;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    /**
     * Builder method for constructing a company from relevant fields in a supplied CompanyRegistrationRequest for a
     * supplied user id
     *
     * @param userId  the user id for which to create a company entity
     * @param request request including all the relevant fields needed to make a company entity
     * @return a new company object based on the supplied user id and request object fields
     */
    public static Company toCompany(UUID userId, CompanyRegistrationRequest request) {
        return Company.builder()
                .userId(userId)
                .name(request.getName())
                .organizationNumber(request.getOrganizationNumber())
                .description(request.getDescription())
                .companyType(request.getCompanyType())
                .build();
    }
}

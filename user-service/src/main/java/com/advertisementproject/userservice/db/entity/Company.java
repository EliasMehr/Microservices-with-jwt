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

    public static Company toCompany(UUID userId, CompanyRegistrationRequest request){
        return Company.builder()
                .userId(userId)
                .name(request.getName())
                .organizationNumber(request.getOrganizationNumber())
                .description(request.getDescription())
                .companyType(request.getCompanyType())
                .build();
    }
}

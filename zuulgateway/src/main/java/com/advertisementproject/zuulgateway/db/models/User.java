package com.advertisementproject.zuulgateway.db.models;

import com.advertisementproject.zuulgateway.api.request.RegistrationRequest;
import com.advertisementproject.zuulgateway.db.models.types.CompanyType;
import com.advertisementproject.zuulgateway.db.models.types.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    private UUID id;

    private String identificationNumber;

    @Size(min = 2, message = "First name cannot be less than 2 characters")
    private String firstName;

    @Size(min = 2, message = "Last name cannot be less than 2 characters")
    private String lastName;

    @Size(min = 2, max = 20, message = "Address cannot be less than 2 characters")
    private String address;

    @Size(min = 2, message = "City cannot be less than 2 characters")
    private String city;

    @Size(min = 5, max = 5, message = "Zip code is required to be 5 digits long")
    private int zipCode;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Must enter an valid email")
    private String email;

    @NotEmpty(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotEmpty
    private String hashedPassword;

    @Enumerated(STRING)
    private Role role;

    @Enumerated(STRING)
    private CompanyType companyType;

    private boolean enabled;

    public static User toUser(RegistrationRequest request, Role role, String hashedPassword) {
        return builder()
                .id(UUID.randomUUID())
                .identificationNumber(request.getIdentificationNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(role)
                .companyType(request.getType())
                .hashedPassword(hashedPassword)
                .enabled(false)
                .build();
    }
}

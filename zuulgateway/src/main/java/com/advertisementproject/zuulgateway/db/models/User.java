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
@Table(name = "users2")
@Entity
public class User {

    @Id
    private UUID id;

    private String identificationNumber;

    @Size(min = 2, max = 20, message = "First name must be 2-20 characters long")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters long")
    private String lastName;

    @Size(min = 2, max = 20, message = "Address must be 2-20 characters long")
    private String address;

    @Size(min = 2, max = 20, message = "City must be 2-20 characters long")
    private String city;

    @Pattern( regexp = "^[0-9]{5}$", message = "Zip code must be 5 digits long")
    private String zipCode;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Must enter an valid email")
    private String email;

    @Pattern( regexp = "^[0-9]{10}$", message = "PhoneNumber must be exactly 10 digits")
    private String phoneNumber;

    @NotEmpty
    private String hashedPassword;

    @Enumerated(STRING)
    private Role role;

    @Enumerated(STRING)
    private CompanyType companyType;

    private boolean enabled = false;

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

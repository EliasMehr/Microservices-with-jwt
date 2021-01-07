package com.advertisementproject.zuulgateway.db.models;

import com.advertisementproject.zuulgateway.db.models.types.CompanyType;
import com.advertisementproject.zuulgateway.db.models.types.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

import static javax.persistence.EnumType.*;

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
    @Size(min = 6, max = 20, message = "Password need to be 6 characters long as minimum")
    private String hashedPassword;

    @Enumerated(STRING)
    private Role role;

    @Enumerated(STRING)
    private CompanyType companyType;

    private boolean enabled;
}

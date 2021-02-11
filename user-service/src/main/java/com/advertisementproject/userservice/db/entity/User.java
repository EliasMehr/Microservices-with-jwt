package com.advertisementproject.userservice.db.entity;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.db.entity.types.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

import static com.advertisementproject.userservice.db.entity.types.Role.COMPANY;
import static com.advertisementproject.userservice.db.entity.types.Role.CUSTOMER;
import static javax.persistence.EnumType.STRING;

/**
 * User entity with core account information about a user. Raw password is transient and ignored for JSON which means
 * that it can be validated along with the rest of the fields but won't show up as JSON or be saved in the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
@Entity
public class User {

    /**
     * Primary id for User entity.
     */
    @Id
    private UUID id;

    /**
     * Email address for the user which must be unique. Used as a username when logging in through the Zuul Gateway
     * application.
     */
    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Must enter an valid email")
    private String email;

    /**
     * Raw password for the user account which is not saved in the database and not exposed as JSON. It only exists in
     * the user class so it can easily be validated along with all the other fields.
     */
    @Transient
    @JsonIgnore
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Password must contain at least one digit [0-9], " +
                    "at least one lowercase Latin character [a-z], " +
                    "at least one uppercase Latin character [A-Z], " +
                    "at least one special character like ! @ # & ( ) " +
                    "and have a length of at least 8 characters and a maximum of 20 characters")
    private String rawPassword;

    /**
     * Hashed password from the raw password the user submitted upon registration.
     */
    @NotNull
    @Size(min = 60, max = 60)
    private String hashedPassword;

    /**
     * Phone number for the user.
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$", message = "PhoneNumber must be exactly 10 digits")
    private String phoneNumber;

    /**
     * The role of the user, which determines access rights within the system, as defined by Zuul Gateway application.
     */
    @Enumerated(STRING)
    private Role role;

    /**
     * Street address for the user.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "Address must be 2-20 characters long")
    private String address;

    /**
     * City for the user address.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "City must be 2-20 characters long")
    private String city;

    /**
     * Zip code or postal code for the user address.
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{5}$", message = "Zip code must be 5 digits long")
    private String zipCode;

    /**
     * Whether the user account is enabled. Set to true if the user's email has been enabled, otherwise false.
     */
    private boolean enabled = false;

    /**
     * Builder method for constructing a user from relevant fields in a supplied CustomerRegistrationRequest for a
     * supplied user id.
     *
     * @param request request including all the relevant fields needed to make a user entity
     * @return a new user object based on the supplied user id and request object fields
     */
    public static User toUser(CustomerRegistrationRequest request) {
        return builder()
                .id(UUID.randomUUID())
                .address(request.getAddress())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(CUSTOMER)
                .rawPassword(request.getPassword())
                .hashedPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()))
                .enabled(false)
                .build();
    }

    /**
     * Builder method for constructing a user from relevant fields in a supplied CompanyRegistrationRequest for a
     * supplied user id.
     *
     * @param request request including all the relevant fields needed to make a user entity
     * @return a new user object based on the supplied user id and request object fields
     */
    public static User toUser(CompanyRegistrationRequest request) {
        return builder()
                .id(UUID.randomUUID())
                .address(request.getAddress())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(COMPANY)
                .rawPassword(request.getPassword())
                .hashedPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()))
                .enabled(false)
                .build();
    }
}

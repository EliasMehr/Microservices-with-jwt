package com.advertisementproject.zuulgateway.db.entity;

import com.advertisementproject.zuulgateway.db.entity.types.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Users are kept up-to-date by receiving messages from Permissions Service application and then updating the
 * table accordingly whenever a user object is created, updated or deleted. Includes core account information, which
 * role the user has and whether the user is enabled.
 */
@Entity
@Table(name = "users")
@Data
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
    private String email;

    /**
     * Hashed password from the raw password the user submitted upon registration. Must match the password the user
     * enters for login.
     */
    private String hashedPassword;

    /**
     * Phone number for the user.
     */
    private String phoneNumber;

    /**
     * The role of the user, which determines access rights within the system, as defined in WebSecurityConfiguration.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Street address for the user.
     */
    private String address;

    /**
     * City for the user address.
     */
    private String city;

    /**
     * Zip code or postal code for the user address.
     */
    private String zipCode;

    /**
     * Whether the user account is enabled. Set to true if the user's email has been enabled, otherwise false.
     */
    private boolean enabled;
}

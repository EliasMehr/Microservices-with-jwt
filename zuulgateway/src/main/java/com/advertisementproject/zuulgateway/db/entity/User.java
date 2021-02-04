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
    @Id
    private UUID id;
    private String email;
    private String hashedPassword;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;
    private String city;
    private String zipCode;
    private boolean enabled;
}

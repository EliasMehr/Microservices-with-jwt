package com.advertisementproject.zuulgateway.db.entity;

import com.advertisementproject.zuulgateway.db.entity.types.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

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

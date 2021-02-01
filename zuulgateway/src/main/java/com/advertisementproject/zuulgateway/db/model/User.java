package com.advertisementproject.zuulgateway.db.model;

import com.advertisementproject.zuulgateway.db.model.types.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String hashedPassword;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;
    private String city;
    private String zipCode;
    private boolean enabled;
}

package com.advertisementproject.zuulgateway.db.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled;
}

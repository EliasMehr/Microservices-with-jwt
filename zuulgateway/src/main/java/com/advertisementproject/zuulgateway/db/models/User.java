package com.advertisementproject.zuulgateway.db.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

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
    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled;
}

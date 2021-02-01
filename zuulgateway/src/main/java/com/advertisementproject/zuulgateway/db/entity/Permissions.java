package com.advertisementproject.zuulgateway.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class Permissions {

    @Id
    private UUID userId;
    private boolean hasPermission;
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;
}

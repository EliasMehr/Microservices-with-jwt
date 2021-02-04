package com.advertisementproject.zuulgateway.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/**
 * Permissions are kept up-to-date by receiving messages from Permissions Service application and then updating the
 * table accordingly whenever a permissions object is created, updated or deleted. Permissions includes which user id,
 * whether permissions are granted and timestamps for creation and latest update.
 */
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

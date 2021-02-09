package com.advertisementproject.zuulgateway.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/**
 * Permissions are kept up-to-date by receiving messages from Permission Service application and then updating the
 * table accordingly whenever a permission object is created, updated or deleted. Permission includes which user id,
 * whether permission is granted and timestamps for creation and latest update.
 */
@Data
@Entity
public class Permission {

    @Id
    private UUID userId;
    private boolean hasPermission;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;
}

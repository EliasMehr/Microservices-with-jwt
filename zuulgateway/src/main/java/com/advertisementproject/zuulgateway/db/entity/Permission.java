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

    /**
     * Primary id for Permission entity, matching the user id
     */
    @Id
    private UUID userId;

    /**
     * Whether the user has permission to use the system. Can be revoked by an admin user if needed to indirectly
     * invalidate jwt tokens and block user in the case of suspicious activity. True if the user has permission,
     * otherwise false.
     */
    private boolean hasPermission;

    /**
     * Timestamp for when the permission was created.
     */
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    /**
     * Timestamp for when the permission was last updated.
     */
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;
}

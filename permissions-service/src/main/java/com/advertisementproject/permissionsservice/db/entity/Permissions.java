package com.advertisementproject.permissionsservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Permissions entity tracks whether a specific user has permissions to log in and use the services provided. The main
 * purpose is to be able to remove permissions and thereby invalidate jwt tokens in the gateway security application.
 * Permissions also provide information about when permissions were granted or altered.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permissions {

    @Id
    private UUID userId;
    @NotNull
    private boolean hasPermission;
    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    /**
     * A builder method that takes a user id and builds permissions for that id.
     *
     * @param userId the user id for which to grant permissions
     * @return permissions object for the supplied user id
     */
    public static Permissions toPermissions(UUID userId) {
        return Permissions.builder()
                .userId(userId)
                .hasPermission(true)
                .createdAt(Instant.now())
                .build();
    }
}

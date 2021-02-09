package com.advertisementproject.permissionservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Permission entity tracks whether a specific user has permission to log in and use the services provided. The main
 * purpose is to be able to remove permission and thereby invalidate jwt tokens in the gateway security application.
 * Permission also provides information about when permission is granted or altered.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {

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
     * A builder method that takes a user id and builds permission for that id.
     *
     * @param userId the user id for which to grant permission
     * @return permission object for the supplied user id
     */
    public static Permission toPermission(UUID userId) {
        return Permission.builder()
                .userId(userId)
                .hasPermission(true)
                .createdAt(Instant.now())
                .build();
    }
}

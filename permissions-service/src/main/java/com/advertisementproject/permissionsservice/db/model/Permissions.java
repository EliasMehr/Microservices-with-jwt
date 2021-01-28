package com.advertisementproject.permissionsservice.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

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
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    public static Permissions toPermissions(UUID userId){
        return Permissions.builder()
                .userId(userId)
                .hasPermission(true)
                .createdAt(Instant.now())
                .build();
    }
}

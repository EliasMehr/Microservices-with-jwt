package com.advertisementproject.zuulgateway.db.models;

import lombok.Data;
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
    private Instant createdAt;
    private Instant updatedAt;
}

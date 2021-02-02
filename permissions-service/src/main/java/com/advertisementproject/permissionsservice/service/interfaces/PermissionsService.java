package com.advertisementproject.permissionsservice.service.interfaces;

import com.advertisementproject.permissionsservice.db.entity.Permissions;

import java.util.UUID;

/**
 * Interface for permissions service. A permissions service should be able to perform CRUD based database operations
 * for permissions objects.
 */
public interface PermissionsService {

    void createPermissions(UUID userId);
    Permissions updatePermissions(UUID userId, boolean hasPermissions);
    Permissions getPermissions(UUID userId);
    void removePermissions(UUID userId);
}

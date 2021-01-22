package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.db.models.Permissions;

import java.util.UUID;

public interface PermissionsService {

    Permissions createPermissions(UUID userId);
    Permissions updatePermissions(UUID userId, boolean hasPermissions);
    Permissions getPermissions(UUID userId);
    void removePermissions(UUID userId);
}

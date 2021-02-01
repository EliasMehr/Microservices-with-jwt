package com.advertisementproject.permissionsservice.service.interfaces;

import com.advertisementproject.permissionsservice.db.entity.Permissions;

import java.util.UUID;

public interface PermissionsService {

    Permissions createPermissions(UUID userId);
    Permissions updatePermissions(UUID userId, boolean hasPermissions);
    Permissions getPermissions(UUID userId);
    void removePermissions(UUID userId);
}

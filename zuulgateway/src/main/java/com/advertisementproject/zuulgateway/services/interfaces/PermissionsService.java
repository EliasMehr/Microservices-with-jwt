package com.advertisementproject.zuulgateway.services.interfaces;

import com.advertisementproject.zuulgateway.db.entity.Permissions;

import java.util.UUID;

public interface PermissionsService {

    Permissions getPermissionsById(UUID userId);
    void saveOrUpdatePermissions(Permissions permissions);
    void deletePermissions(UUID userId);
}

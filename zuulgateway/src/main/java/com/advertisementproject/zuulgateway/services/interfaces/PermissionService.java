package com.advertisementproject.zuulgateway.services.interfaces;

import com.advertisementproject.zuulgateway.db.entity.Permission;

import java.util.UUID;

/**
 * Service for doing CRUD operations for permissions
 */
public interface PermissionService {

    /**
     * Retrieves permission object for the supplied user id
     *
     * @param userId the user id for which to get permission object
     * @return permission object for the supplied user id
     */
    Permission getPermissionById(UUID userId);

    /**
     * Saves or updates permission object in the database
     *
     * @param permission the permission object to save/update
     */
    void saveOrUpdatePermission(Permission permission);

    /**
     * Deletes permission object matching the supplied user id
     *
     * @param userId the user id for the permission object to be deleted
     */
    void deletePermission(UUID userId);
}

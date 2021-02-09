package com.advertisementproject.zuulgateway.services.interfaces;

import com.advertisementproject.zuulgateway.db.entity.Permissions;

import java.util.UUID;

/**
 * Service for doing CRUD operations for permissions
 */
public interface PermissionsService {

    /**
     * Retrieves permissions object for the supplied user id
     *
     * @param userId the user id for which to get permissions object
     * @return permissions object for the supplied user id
     */
    Permissions getPermissionsById(UUID userId);

    /**
     * Saves or updates permissions object in the database
     *
     * @param permissions the permissions object to save/update
     */
    void saveOrUpdatePermissions(Permissions permissions);

    /**
     * Deletes permissions object matching the supplied user id
     *
     * @param userId the user id for the permissions object to be deleted
     */
    void deletePermissions(UUID userId);
}

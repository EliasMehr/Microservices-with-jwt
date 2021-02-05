package com.advertisementproject.permissionsservice.service.interfaces;

import com.advertisementproject.permissionsservice.db.entity.Permissions;

import java.util.UUID;

/**
 * Interface for permissions service. A permissions service should be able to perform CRUD based database operations
 * for permissions objects.
 */
public interface PermissionsService {

    /**
     * Creates permissions for a user in the database and informs other microservices that permissions have been created.
     * @param userId the user id for which to create permissions
     */
    void createPermissions(UUID userId);

    /**
     * Updates permissions for a user in the database and informs other microservices that permissions have been updated.
     * @param userId the user id for which to update permissions
     * @param hasPermissions determines whether permissions should be enabled or disabled
     * @return permissions after they have been altered and saved to the database
     */
    Permissions updatePermissions(UUID userId, boolean hasPermissions);

    /**
     * Retrieves permissions from the database for the supplied user id
     * @param userId the user id for which to get permissions object
     * @return permissions object for the supplied user id
     */
    Permissions getPermissions(UUID userId);

    /**
     * Deletes permissions from the database for the supplied user id and informs other microservices that permissions
     * have been deleted.
     * @param userId the user id for the user to remove permissions for
     */
    void removePermissions(UUID userId);
}

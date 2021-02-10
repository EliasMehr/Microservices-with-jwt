package com.advertisementproject.permissionservice.service.interfaces;

import com.advertisementproject.permissionservice.db.entity.Permission;

import java.util.UUID;

/**
 * Interface for permission service. A permission service should be able to perform CRUD based database operations
 * for permission objects.
 */
public interface PermissionService {

    /**
     * Creates permission for a user in the database and informs other microservices that permission has been created.
     *
     * @param userId the user id for which to create permission
     */
    void createPermission(UUID userId);

    /**
     * Updates permission for a user in the database and informs other microservices that permission has been updated.
     *
     * @param userId         the user id for which to update permission
     * @param hasPermission determines whether permission should be enabled or disabled
     * @return permission after they have been altered and saved to the database
     */
    Permission updatePermission(UUID userId, boolean hasPermission);

    /**
     * Retrieves permission from the database for the supplied user id
     *
     * @param userId the user id for which to get permission object
     * @return permission object for the supplied user id
     */
    Permission getPermission(UUID userId);

    /**
     * Deletes permission from the database for the supplied user id and informs other microservices that permission
     * have been deleted.
     *
     * @param userId the user id for the user to remove permission for
     */
    void removePermission(UUID userId);
}

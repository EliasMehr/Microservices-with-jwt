package com.advertisementproject.permissionservice.service;

import com.advertisementproject.permissionservice.db.entity.Permission;
import com.advertisementproject.permissionservice.db.repository.PermissionRepository;
import com.advertisementproject.permissionservice.exception.PermissionNotFoundException;
import com.advertisementproject.permissionservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.permissionservice.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * PermissionService implementation that allows for CRUD operations and validation for permission
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    /**
     * JPA Repository for managing Permissions.
     */
    private final PermissionRepository permissionRepository;

    /**
     * Service for sending messages to other applications via message broker.
     */
    private final MessagePublisher messagePublisher;

    /**
     * Creates permission for a user in the database and informs other microservices that permission has been created.
     *
     * @param userId the user id for which to create permission
     */
    @Override
    public void createPermission(UUID userId) {
        Permission permission = Permission.toPermission(userId);
        permissionRepository.save(permission);
        messagePublisher.sendPermissionMessage(permission);
        log.info("Permissions saved for user with id: " + userId);
    }

    /**
     * Updates permission for a user in the database and informs other microservices that permission has been updated.
     *
     * @param userId         the user id for which to update permission
     * @param hasPermission determines whether permission should be enabled or disabled
     * @return permission after being altered and saved to the database
     * @throws IllegalArgumentException     if hasPermission for permission object is true and the input also is set to
     *                                      true then a change cannot be made. Same goes for if both are set to false.
     * @throws PermissionNotFoundException if the permission is not found in the database for the supplied user id
     */
    @Override
    public Permission updatePermission(UUID userId, boolean hasPermission) {
        Permission permission = getPermission(userId);
        if (permission.isHasPermission() == hasPermission) {
            throw new IllegalArgumentException("hasPermission is already set to " + hasPermission + ". Cannot update permission");
        }
        permission.setHasPermission(hasPermission);
        permission.setUpdatedAt(Instant.now());

        permissionRepository.save(permission);
        messagePublisher.sendPermissionMessage(permission);
        return permission;
    }

    /**
     * Retrieves permissions from the database for the supplied user id
     *
     * @param userId the user id for which to get permissions object
     * @return permissions object for the supplied user id
     * @throws PermissionNotFoundException if the permissions are not found in the database for the supplied user id
     */
    @Override
    public Permission getPermission(UUID userId) {
        return permissionRepository.findById(userId)
                .orElseThrow(() -> new PermissionNotFoundException("Permissions not found for userId: " + userId));
    }

    /**
     * Deletes permissions from the database for the supplied user id and informs other microservices that permissions
     * have been deleted.
     *
     * @param userId the user id for the user to remove permissions for
     */
    @Override
    public void removePermission(UUID userId) {
        permissionRepository.deleteById(userId);
        messagePublisher.sendPermissionsDeleteMessage(userId);
    }
}

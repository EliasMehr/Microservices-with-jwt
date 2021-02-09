package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.api.exceptions.EntityNotFoundException;
import com.advertisementproject.zuulgateway.db.entity.Permission;
import com.advertisementproject.zuulgateway.db.repositories.PermissionRepository;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for doing CRUD operations for permissions
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    /**
     * Retrieves permission object for the supplied user id
     *
     * @param userId the user id for which to get permission object
     * @return permission object for the supplied user id
     * @throws EntityNotFoundException if the permission is not found
     */
    @Override
    public Permission getPermissionById(UUID userId) {
        return permissionRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Permissions not found for userId: " + userId));
    }

    /**
     * Saves or updates permission object in the database
     *
     * @param permission the permission object to save/update
     */
    @Override
    public void saveOrUpdatePermission(Permission permission) {
        permissionRepository.save(permission);
    }

    /**
     * Deletes permission object matching the supplied user id
     *
     * @param userId the user id for the permission object to be deleted
     */
    @Override
    public void deletePermission(UUID userId) {
        permissionRepository.deleteById(userId);
    }
}

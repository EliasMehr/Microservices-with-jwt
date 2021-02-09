package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.repositories.PermissionsRepository;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for doing CRUD operations for permissions
 */
@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository permissionsRepository;

    /**
     * Retrieves permissions object for the supplied user id
     *
     * @param userId the user id for which to get permissions object
     * @return permissions object for the supplied user id
     * @throws IllegalStateException if the permissions are not found
     */
    @Override
    public Permissions getPermissionsById(UUID userId) {
        return permissionsRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Permissions not found for userId: " + userId));
    }

    /**
     * Saves or updates permissions object in the database
     *
     * @param permissions the permissions object to save/update
     */
    @Override
    public void saveOrUpdatePermissions(Permissions permissions) {
        permissionsRepository.save(permissions);
    }

    /**
     * Deletes permissions object matching the supplied user id
     *
     * @param userId the user id for the permissions object to be deleted
     */
    @Override
    public void deletePermissions(UUID userId) {
        permissionsRepository.deleteById(userId);
    }
}

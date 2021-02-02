package com.advertisementproject.permissionsservice.service;

import com.advertisementproject.permissionsservice.db.entity.Permissions;
import com.advertisementproject.permissionsservice.db.repository.PermissionsRepository;
import com.advertisementproject.permissionsservice.exception.PermissionsNotFoundException;
import com.advertisementproject.permissionsservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * PermissionsService implementation that allows for CRUD operations and validation for permissions
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository permissionsRepository;
    private final MessagePublisher messagePublisher;

    /**
     * Creates permissions for a user in the database and informs other microservices that permissions have been created.
     * @param userId the user id for which to create permissions
     */
    @Override
    public void createPermissions(UUID userId) {
        Permissions permissions = Permissions.toPermissions(userId);
        permissionsRepository.save(permissions);
        messagePublisher.sendPermissionsMessage(permissions);
        log.info("Permissions saved for user with id: " + userId);
    }

    /**
     * Updates permissions for a user in the database and informs other microservices that permissions have been updated.
     * @param userId the user id for which to update permissions
     * @param hasPermissions determines whether permissions should be enabled or disabled
     * @return permissions after they have been altered and saved to the database
     * @throws IllegalArgumentException if hasPermissions for permissions object is true and the input also is set to
     * true then a change cannot be made. Same goes for if both are set to false.
     * @throws PermissionsNotFoundException if the permissions are not found in the database for the supplied user id
     */
    @Override
    public Permissions updatePermissions(UUID userId, boolean hasPermissions) {
        Permissions permissions = getPermissions(userId);
        if(permissions.isHasPermission() == hasPermissions){
            throw new IllegalArgumentException("Permissions are already " + hasPermissions + ". Cannot update permissions");
        }
        permissions.setHasPermission(hasPermissions);
        permissions.setUpdatedAt(Instant.now());

        permissionsRepository.save(permissions);
        messagePublisher.sendPermissionsMessage(permissions);
        return permissions;
    }

    /**
     * Retrieves permissions from the database for the supplied user id
     * @param userId the user id for which to get permissions object
     * @return permissions object for the supplied user id
     * @throws PermissionsNotFoundException if the permissions are not found in the database for the supplied user id
     */
    @Override
    public Permissions getPermissions(UUID userId) {
        return permissionsRepository.findById(userId)
                .orElseThrow(()-> new PermissionsNotFoundException("Permissions not found for userId: " + userId));
    }

    /**
     * Deletes permissions from the database for the supplied user id and informs other microservices that permissions
     * have been deleted.
     * @param userId the user id for the user to remove permissions for
     */
    @Override
    public void removePermissions(UUID userId) {
        permissionsRepository.deleteById(userId);
        messagePublisher.sendPermissionsDeleteMessage(userId);
    }
}

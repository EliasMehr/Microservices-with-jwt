package com.advertisementproject.permissionsservice.service;

import com.advertisementproject.permissionsservice.db.model.Permissions;
import com.advertisementproject.permissionsservice.db.repository.PermissionsRepository;
import com.advertisementproject.permissionsservice.exception.PermissionsNotFoundException;
import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository permissionsRepository;

    @Override
    public Permissions createPermissions(UUID userId) {
        Permissions permissions = Permissions.toPermissions(userId);
        permissionsRepository.save(permissions);
        log.info("Permissions saved for user with id: " + userId);
        return permissions;
    }

    @Override
    public Permissions updatePermissions(UUID userId, boolean hasPermissions) {
        Permissions permissions = getPermissions(userId);
        if(permissions.isHasPermission() == hasPermissions){
            throw new IllegalArgumentException("Permissions are already " + hasPermissions + ". Cannot update permissions");
        }
        permissions.setHasPermission(hasPermissions);
        permissions.setUpdatedAt(Instant.now());
        return permissionsRepository.save(permissions);
    }

    @Override
    public Permissions getPermissions(UUID userId) {
        return permissionsRepository.findById(userId)
                .orElseThrow(()-> new PermissionsNotFoundException("Permissions not found for userId: " + userId));
    }

    @Override
    public void removePermissions(UUID userId) {
        permissionsRepository.deleteById(userId);
    }
}

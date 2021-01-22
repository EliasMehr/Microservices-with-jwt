package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.PermissionsNotFoundException;
import com.advertisementproject.userservice.db.models.Permissions;
import com.advertisementproject.userservice.db.repository.PermissionsRepository;
import com.advertisementproject.userservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository permissionsRepository;

    @Override
    public Permissions createPermissions(UUID userId) {
        Permissions permissions = Permissions.toPermissions(userId);
        return permissionsRepository.save(permissions);
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

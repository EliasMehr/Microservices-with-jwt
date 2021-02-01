package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.repositories.PermissionsRepository;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository permissionsRepository;

    @Override
    public Permissions getPermissionsById(UUID userId) {
        return permissionsRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException("Permissions not found for userId: " + userId));
    }

    @Override
    public void saveOrUpdatePermissions(Permissions permissions) {
        permissionsRepository.save(permissions);
    }

    @Override
    public void deletePermissions(UUID userId) {
        permissionsRepository.deleteById(userId);
    }
}

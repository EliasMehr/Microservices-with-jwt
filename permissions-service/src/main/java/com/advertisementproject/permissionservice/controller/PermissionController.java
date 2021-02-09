package com.advertisementproject.permissionservice.controller;

import com.advertisementproject.permissionservice.db.entity.Permission;
import com.advertisementproject.permissionservice.request.UpdatePermissionRequest;
import com.advertisementproject.permissionservice.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{userId}")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Permission> getPermission(@PathVariable UUID userId) {
        Permission permission = permissionService.getPermission(userId);
        return ResponseEntity.ok(permission);
    }

    @PutMapping
    public ResponseEntity<Permission> updatePermission(@PathVariable UUID userId,
                                                       @Valid @RequestBody UpdatePermissionRequest request) {
        Permission permission = permissionService.updatePermission(userId, request.getHasPermission());
        return ResponseEntity.ok(permission);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePermission(@PathVariable UUID userId) {
        permissionService.removePermission(userId);
        return ResponseEntity.ok("Deleted permission for userId: " + userId);
    }
}

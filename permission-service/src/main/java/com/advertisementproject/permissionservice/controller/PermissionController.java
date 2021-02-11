package com.advertisementproject.permissionservice.controller;

import com.advertisementproject.permissionservice.db.entity.Permission;
import com.advertisementproject.permissionservice.request.UpdatePermissionRequest;
import com.advertisementproject.permissionservice.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller for managing user permissions, only accessible to admin users
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/{userId}")
public class PermissionController {

    /**
     * Service for managing CRUD operations for Permissions.
     */
    private final PermissionService permissionService;

    /**
     * Retrieves permission object for the supplied user id.
     * @param userId the user id for which to retrieve a permission object
     * @return permission object for the supplied user id.
     */
    @GetMapping
    public ResponseEntity<Permission> getPermission(@PathVariable UUID userId) {
        Permission permission = permissionService.getPermission(userId);
        return ResponseEntity.ok(permission);
    }

    /**
     * Updates permission for the supplied user id.
     * @param userId user id for which to update permission.
     * @param request request including a boolean for whether the user should have permission
     * @return newly updated permission for the supplied user id.
     */
    @PutMapping
    public ResponseEntity<Permission> updatePermission(@PathVariable UUID userId,
                                                       @Valid @RequestBody UpdatePermissionRequest request) {
        Permission permission = permissionService.updatePermission(userId, request.getHasPermission());
        return ResponseEntity.ok(permission);
    }

    /**
     * Removes permission for the supplied user id.
     * @param userId the user id for which to remove permission.
     * @return message stating permission has been removed for the supplied user id.
     */
    @DeleteMapping
    public ResponseEntity<String> deletePermission(@PathVariable UUID userId) {
        permissionService.removePermission(userId);
        return ResponseEntity.ok("Deleted permission for userId: " + userId);
    }
}

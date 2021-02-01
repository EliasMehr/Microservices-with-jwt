package com.advertisementproject.permissionsservice.controller;

import com.advertisementproject.permissionsservice.db.entity.Permissions;
import com.advertisementproject.permissionsservice.request.UpdatePermissionsRequest;
import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions/{userId}")
public class PermissionsController {

    private final PermissionsService permissionsService;

    @GetMapping
    public ResponseEntity<Permissions> getPermissions(@PathVariable UUID userId){
        Permissions permissions = permissionsService.getPermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    @PutMapping
    public ResponseEntity<Permissions> updatePermissions(@PathVariable UUID userId,
                                                         @Valid @RequestBody UpdatePermissionsRequest request){
        Permissions permissions = permissionsService.updatePermissions(userId, request.getHasPermission());
        return ResponseEntity.ok(permissions);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePermissions(@PathVariable UUID userId){
        permissionsService.removePermissions(userId);
        return ResponseEntity.ok("Deleted permissions for userId: " + userId);
    }
}

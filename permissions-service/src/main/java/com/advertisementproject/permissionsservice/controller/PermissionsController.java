package com.advertisementproject.permissionsservice.controller;

import com.advertisementproject.permissionsservice.db.entity.Permissions;
import com.advertisementproject.permissionsservice.request.UpdatePermissionsRequest;
import com.advertisementproject.permissionsservice.service.interfaces.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;


/**
 * The permissions controller handles incoming requests from administrators to alter or remove permissions for a user.
 * Normally permissions are granted by this application after the Confirmation Token service application confirms a
 * user's email. This controller mainly lets administrators change a user's permissions in case for example a person's
 * account has been stolen or as part of lockdown for suspicious activity on the account.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions/{userId}")
public class PermissionsController {

    private final PermissionsService permissionsService;

    /**
     * Handles request to retrieve the permissions for a particular user
     * @param userId the id for the user who's permissions is requested.
     * @return response entity containing the permissions object for the user id in question.
     */
    @GetMapping
    public ResponseEntity<Permissions> getPermissions(@PathVariable UUID userId){
        Permissions permissions = permissionsService.getPermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    /**
     * Handles request to update permissions for a user
     * @param userId the id for the user who's permissions will be altered
     * @param request request object containing a boolean value for whether permissions should be granted or taken away.
     *                The request object is validated to not contain a null value.
     * @return response entity containing permissions for the user after they has been altered
     */
    @PutMapping
    public ResponseEntity<Permissions> updatePermissions(@PathVariable UUID userId,
                                                         @Valid @RequestBody UpdatePermissionsRequest request){
        Permissions permissions = permissionsService.updatePermissions(userId, request.getHasPermission());
        return ResponseEntity.ok(permissions);
    }

    /**
     * Handles request to delete permissions for a user
     * @param userId the id for the user who's permissions will be deleted
     * @return response entity containing a message about which id the permissions were removed for
     */
    @DeleteMapping
    public ResponseEntity<String> deletePermissions(@PathVariable UUID userId){
        permissionsService.removePermissions(userId);
        return ResponseEntity.ok("Deleted permissions for userId: " + userId);
    }
}

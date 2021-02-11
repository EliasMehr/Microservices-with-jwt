package com.advertisementproject.permissionservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Simple request object for updating permission for a user. Has a true/false value for whether the user should have
 * permission or not.
 */
@Data
public class UpdatePermissionRequest {

    /**
     * Whether the user should have permission.
     */
    @NotNull
    private final Boolean hasPermission;
}

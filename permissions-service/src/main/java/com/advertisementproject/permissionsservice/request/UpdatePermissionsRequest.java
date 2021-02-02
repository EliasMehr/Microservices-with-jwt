package com.advertisementproject.permissionsservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Simple request object for updating permissions for a user. Has a true/false value for whether the user should have
 * permissions or not.
 */
@Data
public class UpdatePermissionsRequest {

    @NotNull
    private final Boolean hasPermission;
}

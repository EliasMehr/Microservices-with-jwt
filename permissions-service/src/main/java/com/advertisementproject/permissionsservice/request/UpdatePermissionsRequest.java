package com.advertisementproject.permissionsservice.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePermissionsRequest {

    @NotNull
    private final Boolean hasPermission;
}

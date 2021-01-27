package com.advertisementproject.userservice.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePermissionsRequest {

    @NotNull
    private final Boolean hasPermission;
}

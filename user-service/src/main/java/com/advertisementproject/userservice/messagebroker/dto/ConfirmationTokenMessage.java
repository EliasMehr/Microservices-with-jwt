package com.advertisementproject.userservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ConfirmationTokenMessage implements Serializable {

    private UUID userId;
    private String name;
    private String email;
}

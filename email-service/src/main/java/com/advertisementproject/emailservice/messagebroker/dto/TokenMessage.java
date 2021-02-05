package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object received from Confirmation Token Service application including user id and token string
 */
@Data
public class TokenMessage {

    private UUID userId;
    private String token;
}
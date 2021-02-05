package com.advertisementproject.confirmationtokenservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object including a token string and the related user id.
 */
@Data
@AllArgsConstructor
public class TokenMessage {

    private UUID userId;
    private String token;
}

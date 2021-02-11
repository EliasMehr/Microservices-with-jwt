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

    /**
     * The id of the user the token belongs to.
     */
    private UUID userId;

    /**
     * Token to be used by the Email Service application to send a confirmation link email.
     */
    private String token;
}

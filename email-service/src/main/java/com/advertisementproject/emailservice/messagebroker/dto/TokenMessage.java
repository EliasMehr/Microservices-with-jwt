package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object received from Confirmation Token Service application including user id and token string
 */
@Data
public class TokenMessage {

    /**
     * The id of the user to send a confirmation link email to.
     */
    private UUID userId;

    /**
     * The token to be included in the confirmation link in the confirmation link email.
     */
    private String token;
}
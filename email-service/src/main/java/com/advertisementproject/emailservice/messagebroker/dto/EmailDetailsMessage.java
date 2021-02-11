package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object received from User Service application including all email details except token
 */
@Data
public class EmailDetailsMessage {

    /**
     * The id of the user to send a confirmation link email to.
     */
    private UUID userId;

    /**
     * The name of the user to send a confirmation link email to.
     */
    private String name;

    /**
     * The email address to send a confirmation link email to.
     */
    private String email;
}

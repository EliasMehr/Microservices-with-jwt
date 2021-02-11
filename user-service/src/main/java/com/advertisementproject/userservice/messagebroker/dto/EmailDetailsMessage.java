package com.advertisementproject.userservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object including information needed for Email Service application to send a confirmation link message.
 */
@Data
@AllArgsConstructor
public class EmailDetailsMessage {

    /**
     * The id of the user the name and email are related to.
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

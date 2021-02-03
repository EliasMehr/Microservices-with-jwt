package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Data transfer object received from User Service application including all email details except token
 */
@Data
public class EmailDetailsMessage {
    private UUID userId;
    private String name;
    private String email;
}

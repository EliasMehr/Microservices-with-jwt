package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDetailsMessage {
    private UUID userId;
    private String name;
    private String email;
}

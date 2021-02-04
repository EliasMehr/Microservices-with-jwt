package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TokenMessage {

    private UUID userId;
    private String token;
}

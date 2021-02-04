package com.advertisementproject.confirmationtokenservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TokenMessage {

    private UUID userId;
    private String token;
}

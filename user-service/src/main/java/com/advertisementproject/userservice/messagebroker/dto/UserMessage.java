package com.advertisementproject.userservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserMessage {

    private UUID userId;
    private String name;
    private String email;
}

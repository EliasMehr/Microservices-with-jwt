package com.advertisementproject.userservice.messagebroker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmailDetailsMessage {

    @JsonIgnore
    private UUID userId;
    private String name;
    private String email;

    public EmailDetailsMessage(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

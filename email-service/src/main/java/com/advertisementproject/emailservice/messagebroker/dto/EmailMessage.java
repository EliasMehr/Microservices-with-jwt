package com.advertisementproject.emailservice.messagebroker.dto;

import lombok.Data;

@Data
public class EmailMessage {

    private String name;
    private String email;
    private String token;
}

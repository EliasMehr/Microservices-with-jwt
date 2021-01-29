package com.advertisementproject.userservice.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDetailsMessage {

    private String name;
    private String email;
}

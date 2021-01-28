package com.advertisementproject.messagebroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationTokenMessage implements Serializable {

    private UUID userId;
    private String name;
    private String email;
}

package com.advertisementproject.confirmationtokenservice.messagebroker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage {

    //WRITE_ONLY setting allows ObjectMapper to write (serialize) to Object, but not deserialize to String
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID userId;
    private String name;
    private String email;
    private String token;
}

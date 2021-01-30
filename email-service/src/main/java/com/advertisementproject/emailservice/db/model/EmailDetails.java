package com.advertisementproject.emailservice.db.model;

import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class EmailDetails {
    @Id
    private UUID userId;
    private String token;
    private String name;
    private String email;

    public EmailDetails(EmailDetailsMessage emailDetailsMessage) {
        userId = emailDetailsMessage.getUserId();
        name = emailDetailsMessage.getName();
        email = emailDetailsMessage.getEmail();
    }

    public EmailDetails(TokenMessage tokenMessage){
        userId = tokenMessage.getUserId();
        token = tokenMessage.getToken();
    }

}

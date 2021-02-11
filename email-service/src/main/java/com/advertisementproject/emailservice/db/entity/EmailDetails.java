package com.advertisementproject.emailservice.db.entity;

import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Email details includes information needed to send a confirmation link email and is only meant for temporary
 * storage until all details have been received for a user id, by which time an email will be sent and the database row
 * deleted.
 */
@Data
@Entity
@NoArgsConstructor
public class EmailDetails {

    /**
     * Primary id for EmailDetails entity, matching the user id.
     */
    @Id
    private UUID userId;

    /**
     * Token to add to the confirmation link included in the confirmation link email to be sent to the user.
     */
    private String token;

    /**
     * The name of the user to be included in the confirmation link email to be sent to the user.
     */
    private String name;

    /**
     * The email address to send the confirmation link email to.
     */
    private String email;

    /**
     * Constructor creating an email details object with all the information except token, which is left as null.
     *
     * @param emailDetailsMessage email details message received from User Service application
     */
    public EmailDetails(EmailDetailsMessage emailDetailsMessage) {
        userId = emailDetailsMessage.getUserId();
        name = emailDetailsMessage.getName();
        email = emailDetailsMessage.getEmail();
    }

    /**
     * Constructor creating an email details object with user id and token. Remaining fields are left as null.
     *
     * @param tokenMessage token message received from Confirmation Token Service application
     */
    public EmailDetails(TokenMessage tokenMessage) {
        userId = tokenMessage.getUserId();
        token = tokenMessage.getToken();
    }
}

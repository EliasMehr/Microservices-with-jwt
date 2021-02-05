package com.advertisementproject.emailservice.service.interfaces;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;

import java.util.UUID;

/**
 * Service for managing email details in the database
 */
public interface EmailDetailsService {

    /**
     * Saves email details from an email details message to the database for the user id in the message
     * @param emailDetailsMessage email details (except token) to be saved/added to the database for that user id
     */
    void saveDetails(EmailDetailsMessage emailDetailsMessage);

    /**
     * Saves token from a token message to the database for the user id in the message
     * @param tokenMessage message with token and a user id for which the token should be saved in the database
     */
    void saveToken(TokenMessage tokenMessage);

    /**
     * Retrieves full email details including token for a user id if all the information is present, otherwise null.
     * @param userId the user id for which to retrieve full email details
     * @return email details including all the information if available, otherwise null.
     */
    EmailDetails getCompleteDetailsOrNull(UUID userId);

    /**
     * Deletes the supplied email details from the database
     * @param emailDetails the email details object to delete from the database
     */
    void deleteEmailDetails(EmailDetails emailDetails);

}

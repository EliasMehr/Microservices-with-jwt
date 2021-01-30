package com.advertisementproject.emailservice.service.interfaces;

import com.advertisementproject.emailservice.db.model.EmailDetails;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;

import java.util.UUID;

public interface EmailDetailsService {

    void saveDetails(EmailDetailsMessage emailDetailsMessage);
    void saveToken(TokenMessage tokenMessage);
    EmailDetails getCompleteDetailsOrNull(UUID userId);
    void deleteEmailDetails(EmailDetails emailDetails);

}

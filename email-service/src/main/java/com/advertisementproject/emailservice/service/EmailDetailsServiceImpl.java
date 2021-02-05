package com.advertisementproject.emailservice.service;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import com.advertisementproject.emailservice.db.repository.EmailDetailsRepository;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.emailservice.service.interfaces.EmailDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing email details in the database
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailDetailsServiceImpl implements EmailDetailsService {

    private final EmailDetailsRepository emailDetailsRepository;

    /**
     * Saves email details from an email details message to the database for the user id in the message
     * @param emailDetailsMessage email details (except token) to be saved/added to the database for that user id
     */
    @Override
    public void saveDetails(EmailDetailsMessage emailDetailsMessage){
        Optional<EmailDetails> emailDetailsOptional = emailDetailsRepository.findById(emailDetailsMessage.getUserId());
        EmailDetails emailDetails;
        if(emailDetailsOptional.isPresent()){
            emailDetails = emailDetailsOptional.get();
            emailDetails.setName(emailDetails.getName());
            emailDetails.setEmail(emailDetails.getEmail());
        }
        else {
            emailDetails = new EmailDetails(emailDetailsMessage);
        }
        emailDetailsRepository.save(emailDetails);
        log.info("Saved name and email for userId: " + emailDetailsMessage.getUserId());
    }

    /**
     * Saves token from a token message to the database for the user id in the message
     * @param tokenMessage message with token and a user id for which the token should be saved in the database
     */
    @Override
    public void saveToken(TokenMessage tokenMessage){
        Optional<EmailDetails> emailDetailsOptional = emailDetailsRepository.findById(tokenMessage.getUserId());
        EmailDetails emailDetails;
        if (emailDetailsOptional.isPresent()){
            emailDetails = emailDetailsOptional.get();
            emailDetails.setToken(tokenMessage.getToken());
        }
        else {
            emailDetails = new EmailDetails(tokenMessage);
        }
        emailDetailsRepository.save(emailDetails);
        log.info("Saved token for userId: " + tokenMessage.getUserId());
    }

    /**
     * Retrieves full email details including token for a user id if all the information is present, otherwise null.
     * @param userId the user id for which to retrieve full email details
     * @return email details including all the information if available, otherwise null.
     */
    @Override
    public EmailDetails getCompleteDetailsOrNull(UUID userId) {
        Optional<EmailDetails> emailDetailsOptional = emailDetailsRepository.findById(userId);
        if(emailDetailsOptional.isPresent()){
            EmailDetails emailDetails = emailDetailsOptional.get();
            if(emailDetails.getToken() != null && emailDetails.getName() != null && emailDetails.getEmail() != null){
                return emailDetails;
            }
        }
        return null;
    }

    /**
     * Deletes the supplied email details from the database
     * @param emailDetails the email details object to delete from the database
     */
    @Override
    public void deleteEmailDetails(EmailDetails emailDetails) {
        emailDetailsRepository.delete(emailDetails);
        log.info("Deleted email details for userId: " + emailDetails.getUserId());
    }
}

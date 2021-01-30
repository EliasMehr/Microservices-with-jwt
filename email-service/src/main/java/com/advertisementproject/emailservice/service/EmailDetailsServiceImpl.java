package com.advertisementproject.emailservice.service;

import com.advertisementproject.emailservice.db.model.EmailDetails;
import com.advertisementproject.emailservice.db.repository.EmailDetailsRepository;
import com.advertisementproject.emailservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.emailservice.messagebroker.dto.TokenMessage;
import com.advertisementproject.emailservice.service.interfaces.EmailDetailsService;
import com.advertisementproject.emailservice.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailDetailsServiceImpl implements EmailDetailsService {

    private final EmailDetailsRepository emailDetailsRepository;

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

    @Override
    public void deleteEmailDetails(EmailDetails emailDetails) {
        emailDetailsRepository.delete(emailDetails);
        log.info("Deleted email details for userId: " + emailDetails.getUserId());
    }

//    private void sendEmailAndDeleteDatabasePost(EmailDetails emailDetails) {
//        emailService.sendConfirmationLinkEmail(emailDetails.getEmail(), emailDetails.getName(), emailDetails.getToken());
//        emailDetailsRepository.deleteById(emailDetails.getUserId());
//        log.info("Deleted emailDetails for userId: " + emailDetails.getUserId());
//    }
}

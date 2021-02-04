package com.advertisementproject.confirmationtokenservice.service;

import com.advertisementproject.confirmationtokenservice.db.entity.ConfirmationToken;
import com.advertisementproject.confirmationtokenservice.db.repository.ConfirmationTokenRepository;
import com.advertisementproject.confirmationtokenservice.exception.ConfirmationTokenException;
import com.advertisementproject.confirmationtokenservice.service.interfaces.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String generateAndSaveToken(UUID userId) {
        ConfirmationToken confirmationToken = ConfirmationToken.toConfirmationToken(userId);
        confirmationTokenRepository.save(confirmationToken);
        log.info("New Confirmation token saved for userId: " + userId);
        return confirmationToken.getToken();
    }


    @Override
    @Transactional
    public UUID confirmTokenAndGetUserId(String token) {

        ConfirmationToken confirmationToken = getTokenFromDatabase(token);
        validateNoTokenConfirmedForUserId(confirmationToken.getUserId());
        validateTokenNotExpired(confirmationToken);
        confirmationTokenRepository.updateConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
        return confirmationToken.getUserId();

    }

    @Override
    public void deleteAllConfirmationTokensByUserId(UUID userId){
        confirmationTokenRepository.deleteAllByUserId(userId);
        log.info("Confirmation tokens deleted for userId: " + userId);
    }

    private ConfirmationToken getTokenFromDatabase(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenException("Token not found in database", HttpStatus.NOT_FOUND));
    }


    private void validateNoTokenConfirmedForUserId(UUID userId) {
        if (!confirmationTokenRepository.findConfirmationTokenByConfirmedAtNotNullAndUserId(userId).isEmpty()) {
            throw new ConfirmationTokenException("Email is already confirmed", HttpStatus.CONFLICT);
        }
    }

    private void validateTokenNotExpired(ConfirmationToken confirmationToken) {
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenException("Token is expired", HttpStatus.GONE);
        }
    }



}

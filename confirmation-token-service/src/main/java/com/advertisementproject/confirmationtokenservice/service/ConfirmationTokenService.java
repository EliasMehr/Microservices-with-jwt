package com.advertisementproject.confirmationtokenservice.service;

import com.advertisementproject.confirmationtokenservice.db.model.ConfirmationToken;
import com.advertisementproject.confirmationtokenservice.db.repository.ConfirmationTokenRepository;
import com.advertisementproject.confirmationtokenservice.exception.ConfirmationTokenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String generateAndSaveToken(UUID userId) {
        ConfirmationToken confirmationToken = ConfirmationToken.toConfirmationToken(userId);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken.getToken();
    }


    @Transactional
    public UUID confirmTokenAndGetUserId(String token) {

        ConfirmationToken confirmationToken = getTokenFromDatabase(token);
        validateTokenNotConfirmed(confirmationToken);
        validateTokenNotExpired(confirmationToken);
        confirmationTokenRepository.updateConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
        return confirmationToken.getUserId();

    }

    private ConfirmationToken getTokenFromDatabase(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenException("Token not found in database", HttpStatus.NOT_FOUND));
    }


    private void validateTokenNotConfirmed(ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenException("Email is already confirmed", HttpStatus.CONFLICT);
        }
    }

    private void validateTokenNotExpired(ConfirmationToken confirmationToken) {
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenException("Token is expired", HttpStatus.GONE);
        }
    }



}

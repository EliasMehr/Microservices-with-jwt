package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.ConfirmationTokenException;
import com.advertisementproject.userservice.db.models.ConfirmationToken;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String generateAndSaveToken(User user) {
        ConfirmationToken confirmationToken = ConfirmationToken.toConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken.getToken();
    }


    @Transactional
    public UUID confirmTokenAndGetUserId(String token) {

        ConfirmationToken confirmationToken = getTokenFromDatabase(token);
        validateTokenNotConfirmed(confirmationToken);
        validateTokenNotExpired(confirmationToken);
        confirmationTokenRepository.updateConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
        return confirmationToken.getUser().getId();

    }

    private ConfirmationToken getTokenFromDatabase(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenException("Token not found in database"));
    }


    private void validateTokenNotConfirmed(ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
               throw new ConfirmationTokenException("Email is already confirmed");
        }
    }

    private void validateTokenNotExpired(ConfirmationToken confirmationToken) {
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenException("Token is expired");
        }
    }


}

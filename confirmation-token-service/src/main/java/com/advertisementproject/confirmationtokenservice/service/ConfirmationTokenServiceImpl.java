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

/**
 * Service implementation for managing confirmation tokens in the database
 */
@Slf4j
@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    /**
     * JPA Repository for confirmation tokens.
     */
    private final ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Generates and saves a confirmation token object in the database for a supplied user id, then returns token string
     *
     * @param userId the user id for which to create and save a confirmation token.
     * @return the token string for the generated confirmation token.
     */
    @Override
    public String generateAndSaveToken(UUID userId) {
        ConfirmationToken confirmationToken = ConfirmationToken.toConfirmationToken(userId);
        confirmationTokenRepository.save(confirmationToken);
        log.info("New Confirmation token saved for userId: " + userId);
        return confirmationToken.getToken();
    }

    /**
     * Finds a confirmation token based on token string, sets the token to confirmed and retrieves user id.
     *
     * @param token the token string for the confirmation token to set to confirmed
     * @return the user id of the newly confirmed token
     * @throws ConfirmationTokenException if confirmation token is not found, token has expired or the user id already
     *                                    has a confirmed token
     */
    @Override
    @Transactional
    public UUID confirmTokenAndGetUserId(String token) {

        ConfirmationToken confirmationToken = getTokenFromDatabase(token);
        validateNoTokenConfirmedForUserId(confirmationToken.getUserId());
        validateTokenNotExpired(confirmationToken);
        confirmationTokenRepository.updateConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
        return confirmationToken.getUserId();

    }

    /**
     * Deletes all tokens for the supplied user id
     *
     * @param userId the user id for which to delete all confirmation tokens
     */
    @Override
    public void deleteAllConfirmationTokensByUserId(UUID userId) {
        confirmationTokenRepository.deleteAllByUserId(userId);
        log.info("Confirmation tokens deleted for userId: " + userId);
    }

    /**
     * Helper method to retrieve confirmation token from database
     *
     * @param token the token string for which to retrieve a confirmation token object
     * @return the confirmation token for the supplied token string
     * @throws ConfirmationTokenException with status NOT_FOUND if token is not found
     */
    private ConfirmationToken getTokenFromDatabase(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenException("Token not found in database", HttpStatus.NOT_FOUND));
    }


    /**
     * Helper method to validate that the user doesn't have any confirmed tokens already
     *
     * @param userId the user id for which to validate that no tokens have been confirmed
     * @throws ConfirmationTokenException with status CONFLICT if any confirmed token is found for the supplied user id
     */
    private void validateNoTokenConfirmedForUserId(UUID userId) {
        if (!confirmationTokenRepository.findConfirmationTokenByConfirmedAtNotNullAndUserId(userId).isEmpty()) {
            throw new ConfirmationTokenException("Email is already confirmed", HttpStatus.CONFLICT);
        }
    }

    /**
     * Helper method to validate that a token is not expired
     *
     * @param confirmationToken the confirmation token to validate not expired
     * @throws ConfirmationTokenException with status GONE if token is not valid
     */
    private void validateTokenNotExpired(ConfirmationToken confirmationToken) {
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenException("Token is expired", HttpStatus.GONE);
        }
    }


}

package com.advertisementproject.confirmationtokenservice.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for managing confirmation tokens in the database
 */
public interface ConfirmationTokenService {
    /**
     * Generates and saves a confirmation token object in the database for a supplied user id, then returns token string
     *
     * @param userId the user id for which to create and save a confirmation token.
     * @return the token string for the generated confirmation token.
     */
    String generateAndSaveToken(UUID userId);

    /**
     * Finds a confirmation token based on token string, sets the token to confirmed and retrieves user id.
     *
     * @param token the token string for the confirmation token to set to confirmed
     * @return the user id related to the newly confirmed token
     */
    @Transactional
    UUID confirmTokenAndGetUserId(String token);

    /**
     * Deletes all tokens for the supplied user id
     *
     * @param userId the user id for which to delete all confirmation tokens
     */
    void deleteAllConfirmationTokensByUserId(UUID userId);
}

package com.advertisementproject.confirmationtokenservice.db.repository;

import com.advertisementproject.confirmationtokenservice.db.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing confirmation tokens in the database
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {

    /**
     * Retrieves an optional confirmation token object for a supplied token string
     *
     * @param token the token string for which to retrieve a confirmation token object
     * @return the confirmation token object matching the supplied token, or an empty optional if there is no match.
     */
    Optional<ConfirmationToken> findByToken(String token);

    /**
     * Retrieves all confirmation tokens that have been confirmed for a supplied user id.
     *
     * @param userId the user id for which to retrieve all confirmed tokens
     * @return list of confirmed tokens for a supplied user id
     */
    List<ConfirmationToken> findConfirmationTokenByConfirmedAtNotNullAndUserId(UUID userId);

    /**
     * Sets a specific confirmation token to confirmedAt = supplied timestamp (which should be current timestamp)
     * @param token the token to confirm
     * @param confirmedAt the confirmedAt time to set (should be current timestamp)
     */
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    void updateConfirmedAt(String token,
                           LocalDateTime confirmedAt);

    /**
     * Deletes all confirmation tokens for a specific user id
     * @param userId the user id for which to delete all confirmation tokens
     */
    @Transactional
    void deleteAllByUserId(UUID userId);
}

package com.advertisementproject.confirmationtokenservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity including a confirmation token, a user id and timestamp information related to the token
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    /**
     * Primary id for the ConfirmationToken entity.
     */
    @Id
    private UUID id;

    /**
     * The token that needs to be supplied in the confirmation link that the user clicks on to confirm their email.
     */
    @NotNull
    private String token;

    /**
     * Timestamp for when the confirmation token was created.
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * Timestamp for when the confirmation token expires.
     */
    @NotNull
    private LocalDateTime expiresAt;

    /**
     * Timestamp for when the confirmation token was confirmed.
     */
    private LocalDateTime confirmedAt;

    /**
     * The id of the user who's account the confirmation token is meant to confirm.
     */
    private UUID userId;

    /**
     * Builder method for constructing a confirmation token object using a supplied user id.
     *
     * @param userId the user id for which to create a token.
     * @return a token object for the supplied user id.
     */
    public static ConfirmationToken toConfirmationToken(UUID userId) {
        return ConfirmationToken.builder()
                .id(UUID.randomUUID())
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
    }


}

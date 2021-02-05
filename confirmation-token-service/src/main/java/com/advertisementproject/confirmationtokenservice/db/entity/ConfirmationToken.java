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

    @Id
    private UUID id;

    @NotNull
    private String token;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    private UUID userId;

    /**
     * Builder method for constructing a confirmation token object using a supplied user id.
     * @param userId the user id for which to create a token.
     * @return a token object for the supplied user id.
     */
    public static ConfirmationToken toConfirmationToken(UUID userId){
        return ConfirmationToken.builder()
                .id(UUID.randomUUID())
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
    }



}

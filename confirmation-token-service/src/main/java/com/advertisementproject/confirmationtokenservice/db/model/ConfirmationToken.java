package com.advertisementproject.confirmationtokenservice.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

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

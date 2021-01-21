package com.advertisementproject.userservice.db.models;

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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public static ConfirmationToken toConfirmationToken(User user){
        return ConfirmationToken.builder()
                .id(UUID.randomUUID())
                .token(UUID.randomUUID().toString())
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
    }



}

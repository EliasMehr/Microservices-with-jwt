package com.advertisementproject.confirmationtokenservice.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ConfirmationTokenService {
    String generateAndSaveToken(UUID userId);

    @Transactional
    UUID confirmTokenAndGetUserId(String token);

    void deleteAllConfirmationTokensByUserId(UUID userId);
}

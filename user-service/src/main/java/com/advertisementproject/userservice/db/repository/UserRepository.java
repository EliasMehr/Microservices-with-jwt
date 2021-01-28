package com.advertisementproject.userservice.db.repository;

import com.advertisementproject.userservice.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User user " +
            "SET user.enabled = TRUE " +
            "WHERE user.id = ?1")
    void enableUser(UUID userId);
}

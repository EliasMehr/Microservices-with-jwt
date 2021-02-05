package com.advertisementproject.userservice.db.repository;

import com.advertisementproject.userservice.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Standard JPA Repository for doing CRUD operations for users in the database. Includes some custom queries to find
 * a user by email or to enable a specific user.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Retrieves an optional user by email
     * @param email the email for which to retrieve an optional user
     * @return a user optional or an empty optional if the supplied email doesn't match any user in the database
     */
    Optional<User> findByEmail(String email);

    /**
     * Sets enabled = true for a specific user
     * @param userId the user id for which to set enabled = true
     */
    @Transactional
    @Modifying
    @Query("UPDATE User user " +
            "SET user.enabled = TRUE " +
            "WHERE user.id = ?1")
    void enableUser(UUID userId);
}

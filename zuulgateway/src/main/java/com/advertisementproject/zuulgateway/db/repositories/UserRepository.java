package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Standard JPA Repository for getting and modifying users in the database. Includes option to find a user by email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves an optional for a user including the user if the email matches, otherwise an empty optional.
     *
     * @param email the email for which to retrieve a user optional.
     * @return user optional containing a user matching the email or an empty optional if there is no match.
     */
    Optional<User> findByEmail(String email);
}

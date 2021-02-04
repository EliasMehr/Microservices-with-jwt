package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Standard JPA Repository for getting and modifying users in the database. Includes option to find a user by email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}

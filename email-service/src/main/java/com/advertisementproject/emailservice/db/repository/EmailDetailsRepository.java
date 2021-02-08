package com.advertisementproject.emailservice.db.repository;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA repository for doing CRUD operations in the database for email details
 */
@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails, UUID> {

    @Modifying
    @Query("UPDATE EmailDetails e SET e.email = ?1, e.name = ?2 WHERE e.userId = ?3")
    void updateEmailSetNameAndEmail(String name, String email, UUID userId);

    @Modifying
    @Query("UPDATE EmailDetails e SET e.token = ?1 WHERE e.userId = ?2")
    void updateEmailSetToken(String token, UUID userId);
}

package com.advertisementproject.emailservice.db.repository;

import com.advertisementproject.emailservice.db.entity.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA repository for doing CRUD operations in the database for email details
 */
@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails, UUID> {}

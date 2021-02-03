package com.advertisementproject.userservice.db.repository;

import com.advertisementproject.userservice.db.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA Repository for doing CRUD operations for companies in the database
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
package com.advertisementproject.campaignservice.db.repository;

import com.advertisementproject.campaignservice.db.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA Repository for getting and modifying companies in the database
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}

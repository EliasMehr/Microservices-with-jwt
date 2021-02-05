package com.advertisementproject.userservice.db.repository;

import com.advertisementproject.userservice.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA Repository for doing CRUD operations for customers in the database
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}

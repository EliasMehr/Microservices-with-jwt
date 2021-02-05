package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * Standard JPA Repository for getting and modifying permissions in the database
 */
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}

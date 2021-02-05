package com.advertisementproject.permissionsservice.db.repository;

import com.advertisementproject.permissionsservice.db.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA Repository for getting and modifying permissions in the database
 */
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}
package com.advertisementproject.permissionservice.db.repository;

import com.advertisementproject.permissionservice.db.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Standard JPA Repository for getting and modifying permissions in the database
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
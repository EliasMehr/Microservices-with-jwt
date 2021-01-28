package com.advertisementproject.permissionsservice.db.repository;

import com.advertisementproject.permissionsservice.db.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}
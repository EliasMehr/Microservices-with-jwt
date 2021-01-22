package com.advertisementproject.userservice.db.repository;

import com.advertisementproject.userservice.db.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}

package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
@Transactional(readOnly = true)
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
}

package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.models.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, UUID> {
    Optional<UserDetailsImpl> findByEmail(String email);
}

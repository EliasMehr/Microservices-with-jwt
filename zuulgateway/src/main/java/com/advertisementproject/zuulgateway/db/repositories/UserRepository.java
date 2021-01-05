package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}

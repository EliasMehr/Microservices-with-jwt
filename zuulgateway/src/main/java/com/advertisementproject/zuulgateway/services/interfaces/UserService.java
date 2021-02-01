package com.advertisementproject.zuulgateway.services.interfaces;

import com.advertisementproject.zuulgateway.db.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(UUID userId);
    void saveOrUpdateUser(User user);
    void deleteUser(UUID userId);
}

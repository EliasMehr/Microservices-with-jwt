package com.advertisementproject.zuulgateway.services.interfaces;

import com.advertisementproject.zuulgateway.db.entity.User;

import java.util.UUID;

/**
 * Service for doing CRUD operations for permissions
 */
public interface UserService {

    /**
     * Retrieves user matching the supplied email
     *
     * @param email the email for which to get a user
     * @return user matching the supplied email
     */
    User getUserByEmail(String email);

    /**
     * Retrieves user for the supplied user id
     *
     * @param userId the user id for which to get user
     * @return user with the supplied user id
     */
    User getUserById(UUID userId);

    /**
     * Saves or updates user in the database
     *
     * @param user the user to save/update
     */
    void saveOrUpdateUser(User user);

    /**
     * Deletes user matching the supplied user id
     *
     * @param userId the user id for which to delete a user
     */
    void deleteUser(UUID userId);
}

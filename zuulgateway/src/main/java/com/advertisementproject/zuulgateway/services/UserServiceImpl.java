package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.api.exceptions.EntityNotFoundException;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import com.advertisementproject.zuulgateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for doing CRUD operations for users
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves user matching the supplied email
     *
     * @param email the email for which to get a user
     * @return user matching the supplied email
     * @throws UsernameNotFoundException if the username (email) doesn't match any user in the database
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Retrieves user for the supplied user id
     *
     * @param userId the user id for which to get user
     * @return user with the supplied user id
     * @throws EntityNotFoundException if the user is not found for the supplied user id
     */
    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Saves or updates user in the database
     *
     * @param user the user to save/update
     */
    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    /**
     * Deletes user matching the supplied user id
     *
     * @param userId the user id for which to delete a user
     */
    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}

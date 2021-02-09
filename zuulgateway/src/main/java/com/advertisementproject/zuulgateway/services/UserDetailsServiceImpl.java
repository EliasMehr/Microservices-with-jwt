package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionsService;
import com.advertisementproject.zuulgateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of user details service. Retrieves user and permissions from database, then creates and returns a
 * user details object. User can be found either by supplying a user id or a username (email).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final PermissionsService permissionsService;

    /**
     * Retrieves user by username (email), retrieves permissions based on the user id and then creates a user details
     * object that is returned
     *
     * @param email the email for which to find a user
     * @return user details object containing a user and security information
     * @throws UsernameNotFoundException if the username (email) doesn't match any existing user
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    /**
     * Retrieves user by user id, retrieves permissions based on the user id and then creates a user details
     * object that is returned
     *
     * @param userId the user id for which to find a user
     * @return user details object containing a user and security information
     * @throws IllegalStateException if a user is not found for the supplied user id
     */
    public UserDetailsImpl loadUserById(UUID userId) throws IllegalStateException {
        User user = userService.getUserById(userId);
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    /**
     * Helper method to retrieve permissions for a supplied user id using PermissionsService
     *
     * @param userId the user id for which to retrieve permissions
     * @return permissions matching the supplied user id
     */
    private Permissions getPermissions(UUID userId) {
        return permissionsService.getPermissionsById(userId);
    }
}

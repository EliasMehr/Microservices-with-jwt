package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.db.entity.Permission;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
import com.advertisementproject.zuulgateway.services.interfaces.PermissionService;
import com.advertisementproject.zuulgateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of user details service. Retrieves user and permission from database, then creates and returns a
 * user details object. User can be found either by supplying a user id or a username (email).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Service for managing CRUD operations for users.
     */
    private final UserService userService;

    /**
     * Service for managing CRUD operations for permissions.
     */
    private final PermissionService permissionService;

    /**
     * Retrieves user by username (email), retrieves permission based on the user id and then creates a user details
     * object that is returned
     *
     * @param email the email for which to find a user
     * @return user details object containing a user and security information
     * @throws UsernameNotFoundException if the username (email) doesn't match any existing user
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        Permission permission = permissionService.getPermissionById(user.getId());
        return new UserDetailsImpl(user, permission.isHasPermission());
    }

    /**
     * Retrieves user by user id, retrieves permission based on the user id and then creates a user details
     * object that is returned
     *
     * @param userId the user id for which to find a user
     * @return user details object containing a user and security information
     * @throws IllegalStateException if a user is not found for the supplied user id
     */
    public UserDetailsImpl loadUserById(UUID userId) throws IllegalStateException {
        User user = userService.getUserById(userId);
        Permission permission = permissionService.getPermissionById(userId);
        return new UserDetailsImpl(user, permission.isHasPermission());
    }
}

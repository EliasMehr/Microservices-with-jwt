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

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final PermissionsService permissionsService;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    public UserDetailsImpl loadUserById(UUID userId) throws UsernameNotFoundException {
        User user = userService.getUserById(userId);
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    private Permissions getPermissions(UUID userId) {
        return permissionsService.getPermissionsById(userId);
    }
}

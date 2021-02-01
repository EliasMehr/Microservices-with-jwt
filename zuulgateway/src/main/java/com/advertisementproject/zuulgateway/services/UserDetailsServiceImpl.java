package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.db.entity.Permissions;
import com.advertisementproject.zuulgateway.db.entity.User;
import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
import com.advertisementproject.zuulgateway.db.repositories.PermissionsRepository;
import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionsRepository permissionsRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    public UserDetailsImpl loadUserById(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Permissions permissions = getPermissions(user.getId());
        return new UserDetailsImpl(user, permissions.isHasPermission());
    }

    private Permissions getPermissions(UUID userId){
        return permissionsRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException("Permissions not found for userId: " + userId));
    }
}

package com.advertisementproject.zuulgateway.security.model;

import com.advertisementproject.zuulgateway.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

/**
 * Custom implementation of user details including a user object and whether the user is enabled and has permissions.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private User user;
    private boolean hasPermission;

    /**
     * Gets a singleton list of the user's role as a granted authority
     * @return a singleton list of the user's role as a granted authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    /**
     * Gets the hashed password for the user
     * @return the hashed password for the user
     */
    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    /**
     * Gets the username (email) for the user
     * @return the username (email) for the user
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Gets a true or false value for whether the account is non-expired
     * @return always true, since we don't have expiration dates on accounts
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Gets a true or false value for whether the account is non-locked, determined in our case by whether the user
     * has valid permissions
     * @return true if the user has valid permissions, otherwise false
     */
    @Override
    public boolean isAccountNonLocked() {
        return hasPermission;
    }

    /**
     * Gets a true or false value for whether the credentials are non-expired
     * @return always true, since we don't have expiration dates on credentials
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Gets a true or false value for whether the user is enabled
     * @return true if the user is enabled, otherwise false
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
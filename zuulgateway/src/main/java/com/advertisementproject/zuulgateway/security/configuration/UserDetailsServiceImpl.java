package com.advertisementproject.zuulgateway.security.configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /*
        IMPLEMENTATION OF "DATABASE" TO FETCH USER, STARTERS USE IN-Memory USER
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("foo", "foo", new ArrayList<>());
    }

    //Test commit via intellij
}

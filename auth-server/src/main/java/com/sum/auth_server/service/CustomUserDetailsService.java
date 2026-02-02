package com.sum.auth_server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sum.auth_server.entity.UserEntity;
import com.sum.auth_server.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = repo.findByUsername(username);

        UserEntity user = userOptional.orElseThrow(() -> 
            new UsernameNotFoundException("Username " + username + " not found"));

        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .build();
    }
    
}

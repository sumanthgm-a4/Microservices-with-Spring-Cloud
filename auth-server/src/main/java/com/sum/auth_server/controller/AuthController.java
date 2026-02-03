package com.sum.auth_server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sum.auth_server.entity.Role;
import com.sum.auth_server.entity.UserEntity;
import com.sum.auth_server.repository.RoleRepo;
import com.sum.auth_server.repository.UserRepo;
import com.sum.auth_server.utility.JwtUtil;
import com.sum.auth_server.dto.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔐 REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();

        // 🔹 If roles missing or empty → assign USER
        if (request.getRoles() == null || request.getRoles().isEmpty()) {

            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("ROLE USER not found"));

            roles.add(userRole);

        } else {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName.toUpperCase())
                        .orElseThrow(() ->
                            new RuntimeException("ROLE " + roleName + " not found")
                        );
                roles.add(role);
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // 🔑 LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // 1️⃣ Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2️⃣ Load user with roles
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3️⃣ Convert roles → authorities
        List<? extends GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .toList();

        var userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

        // 4️⃣ Generate JWT WITH roles
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

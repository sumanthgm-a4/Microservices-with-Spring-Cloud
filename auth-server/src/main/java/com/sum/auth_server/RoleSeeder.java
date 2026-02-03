package com.sum.auth_server;

import org.springframework.stereotype.Component;

import com.sum.auth_server.entity.Role;
import com.sum.auth_server.repository.RoleRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepo roleRepo;

    @PostConstruct
    public void seedRoles() {
        if (roleRepo.findByName("USER").isEmpty()) {
            roleRepo.save(new Role(null, "USER"));
        }
        if (roleRepo.findByName("ADMIN").isEmpty()) {
            roleRepo.save(new Role(null, "ADMIN"));
        }
        if (roleRepo.findByName("MANAGER").isEmpty()) {
            roleRepo.save(new Role(null, "MANAGER"));
        }
    }
}

package com.sum.auth_server.dto;

import java.util.Set;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Set<String> roles;
}

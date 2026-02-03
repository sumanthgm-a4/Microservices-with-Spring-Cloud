package com.sum.auth_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-resources")
public class SampleController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Auth Server";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String sayAdminHello() {
        return "If you're an admin you can see this";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/manager-admin")
    public String sayManagerAdminHello() {
        return "If you're a manager or an admin you can see this";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/manager")
    public String sayManagerOnlyHello() {
        return "If you're a manager you can see this";
    }
}

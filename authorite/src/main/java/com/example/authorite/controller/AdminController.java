package com.example.authorite.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Welcome Admin!";
    }

    @GetMapping("/management")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String adminManagement() {
        return "Welcome to Management!";
    }
}

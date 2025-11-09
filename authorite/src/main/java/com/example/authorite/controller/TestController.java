package com.example.authorite.controller;

import com.example.authorite.dto.UserResponseDto;
import com.example.authorite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'OWN_PROFILE')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasPermission(#department, 'DEPARTMENT_ACCESS')")
    public ResponseEntity<List<UserResponseDto>> getByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(userService.getUsersByDepartment(department));
    }

    @GetMapping("/active-zone")
    @PreAuthorize("hasPermission(null, 'ACTIVE_USER')")
    public String activeUserOnly() {
        return "Welcome to the active users area!";
    }

    //@GetMapping("/active-zone")
    //@PreAuthorize("hasRole('ADMIN') and hasPermission(#department, 'DEPARTMENT_ACCESS')")
    //public String activeAdminDepartmentOnly() {
    //    return "Welcome to the Admin Department!";
    //}

}

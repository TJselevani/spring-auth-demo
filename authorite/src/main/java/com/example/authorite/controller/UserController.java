package com.example.authorite.controller;

import com.example.authorite.dto.*;
import com.example.authorite.service.AuthService;
import com.example.authorite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/one")
    public ResponseEntity<UserResponseDto> getOneUser(
            @RequestParam String username) {
        return ResponseEntity.ok(userService.getOneUser(username));
    }
}

package com.example.authorite.service;

import com.example.authorite.config.Role;
import com.example.authorite.dto.*;
import com.example.authorite.entity.User;
import com.example.authorite.exception.ApiException;
import com.example.authorite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserResponseDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found"));
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.isActive())
                .department(user.getDepartment())
                .build();
    }

    public UserResponseDto getOneUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found"));
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.isActive())
                .department(user.getDepartment())
                .build();
    }

    public List<UserResponseDto> getAllUsers() {
        var users = userRepository.findAll();

        return users.stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .active(user.isActive())
                        .department(user.getDepartment())
                        .build())
                .toList();
    }

    public List<UserResponseDto> getUsersByDepartment(String department) {
        var users = userRepository.findByDepartment(department);

        return users.stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .active(user.isActive())
                        .department(user.getDepartment())
                        .build())
                .toList();
    }

    public void createUser(UserRequestDto dto, String encodedPassword) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ApiException("User already exists");
        }

        Role role;
        try {
            role = Role.valueOf(dto.getRole().toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new ApiException("Invalid role specified. Must be USER or ADMIN.");
        }

        var user = User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .role(role)
                .active(dto.isActive())
                .department(dto.getDepartment())
                .build();

        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

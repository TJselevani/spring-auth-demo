package com.example.authorite.dto;

import com.example.authorite.config.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private Role role;
    private String department;
    private boolean active;
}

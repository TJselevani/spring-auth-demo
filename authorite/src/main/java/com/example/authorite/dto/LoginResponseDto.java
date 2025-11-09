package com.example.authorite.dto;

import com.example.authorite.config.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private String username;
    private Role role;
}

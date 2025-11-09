package com.example.authorite.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private String role;
    private String department;
    private boolean active;
}

package com.example.authorite.service;

import com.example.authorite.dto.*;
import com.example.authorite.exception.ApiException;
import com.example.authorite.repository.UserRepository;
import com.example.authorite.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public void register(UserRequestDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ApiException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        userService.createUser(dto, encodedPassword);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }

        var user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ApiException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername());

        return LoginResponseDto.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}

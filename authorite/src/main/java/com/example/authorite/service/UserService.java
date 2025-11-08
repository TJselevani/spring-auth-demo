package com.example.authorite.service;

import com.example.authorite.dto.*;
import com.example.authorite.entity.User;
import com.example.authorite.exception.ApiException;
import com.example.authorite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserResponseDto getOneUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found"));
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public void createUser(UserRequestDto dto, String encodedPassword) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ApiException("User already exists");
        }

        var user = User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .role(dto.getRole())
                .build();

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}

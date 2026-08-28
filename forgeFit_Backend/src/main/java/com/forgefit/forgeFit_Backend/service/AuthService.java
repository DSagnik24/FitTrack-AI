package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.AuthResponse;
import com.forgefit.forgeFit_Backend.dto.RegisterRequest;
import com.forgefit.forgeFit_Backend.entity.Role;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .token(null)
                .build();
    }
}
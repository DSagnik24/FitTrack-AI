package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.AuthResponse;
import com.forgefit.forgeFit_Backend.dto.RegisterRequest;
import com.forgefit.forgeFit_Backend.dto.UserResponse;
import com.forgefit.forgeFit_Backend.entity.Role;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.forgefit.forgeFit_Backend.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->
                        new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .user(UserResponse.builder()
                        .id(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole())
                .build())
                .build();
    }
}
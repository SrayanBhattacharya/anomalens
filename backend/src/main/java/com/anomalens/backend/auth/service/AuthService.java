package com.anomalens.backend.auth.service;

import com.anomalens.backend.auth.dto.AuthResponse;
import com.anomalens.backend.auth.dto.LoginRequest;
import com.anomalens.backend.auth.dto.RegisterRequest;
import com.anomalens.backend.auth.exception.EmailAlreadyExistsException;
import com.anomalens.backend.auth.exception.UsernameAlreadyExistsException;
import com.anomalens.backend.users.entity.Role;
import com.anomalens.backend.users.entity.User;
import com.anomalens.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists: " + request.email());
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + request.username());
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        return new AuthResponse("User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        return new AuthResponse("User logged in successfully");
    }
}

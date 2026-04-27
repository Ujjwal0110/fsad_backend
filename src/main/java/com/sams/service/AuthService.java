package com.sams.service;

import com.sams.dto.*;
import com.sams.entity.LoginAudit;
import com.sams.entity.User;
import com.sams.exception.ResourceNotFoundException;
import com.sams.repository.LoginAuditRepository;
import com.sams.repository.UserRepository;
import com.sams.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LoginAuditRepository loginAuditRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(User.Role.valueOf(request.getRole().toUpperCase()))
            .department(request.getDepartment())
            .enrollmentNo(request.getEnrollmentNo())
            .phone(request.getPhone())
            .build();

        user = userRepository.save(user);
        String token = jwtTokenProvider.generateTokenFromEmail(user.getEmail());

        return AuthResponse.builder()
            .token(token)
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication;
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        String role = userOptional.map(user -> user.getRole().name()).orElse("UNKNOWN");

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException ex) {
            recordLoginAttempt(request.getEmail(), role, false);
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(authentication);
        User user = userOptional.orElseGet(() -> userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        recordLoginAttempt(user.getEmail(), user.getRole().name(), true);

        return AuthResponse.builder()
            .token(token)
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }

    private void recordLoginAttempt(String email, String role, boolean success) {
        LoginAudit audit = LoginAudit.builder()
            .email(email)
            .role(role)
            .success(success)
            .build();
        loginAuditRepository.save(audit);
    }
}

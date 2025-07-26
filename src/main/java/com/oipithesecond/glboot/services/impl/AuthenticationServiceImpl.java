package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.dto.AuthResponse;
import com.oipithesecond.glboot.domain.dto.SignupRequest;
import com.oipithesecond.glboot.domain.entities.User;
import com.oipithesecond.glboot.repositories.UserRepository;
import com.oipithesecond.glboot.security.GLUserDetails;
import com.oipithesecond.glboot.security.JwtService;
import com.oipithesecond.glboot.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Correctly depends on the separate JwtService

    @Override
    public UserDetails authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userRepository.findByUsername(username)
                .map(GLUserDetails::new)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User newUser = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
        UserDetails userDetails = new GLUserDetails(newUser);

        // This works because generateToken correctly returns an AuthResponse
        return generateToken(userDetails);
    }

    @Override
    public AuthResponse generateToken(UserDetails userDetails) {
        String token = jwtService.generateToken(userDetails);
        long expiresIn = jwtService.getJwtExpiration();

        return AuthResponse.builder()
                .token(token)
                .expiresIn(expiresIn)
                .build();
    }
}

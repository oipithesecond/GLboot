package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.AuthResponse;
import com.oipithesecond.glboot.domain.dto.LoginRequest;
import com.oipithesecond.glboot.domain.dto.SignupRequest;
import com.oipithesecond.glboot.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        AuthResponse authResponse = authenticationService.generateToken(userDetails);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        AuthResponse authResponse = authenticationService.signup(signupRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}

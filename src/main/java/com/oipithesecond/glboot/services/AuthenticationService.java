package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.dto.AuthResponse;
import com.oipithesecond.glboot.domain.dto.SignupRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    AuthResponse signup(SignupRequest signupRequest);
    AuthResponse generateToken(UserDetails userDetails);
}

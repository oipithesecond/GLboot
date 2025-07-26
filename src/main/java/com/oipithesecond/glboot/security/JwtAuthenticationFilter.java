package com.oipithesecond.glboot.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that runs once per request to validate the JWT in the Authorization header.
 * If the token is valid, it sets the user authentication in the Spring Security context.
 */
@Component // Marks this class as a Spring component to be managed by the container.
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // If there's no Authorization header or it doesn't start with "Bearer ", continue to the next filter.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extract the token from "Bearer <token>"

        try {
            username = jwtService.extractUsername(jwt);

            // If a username is extracted and the user is not already authenticated, process the token.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // If the token is valid for the user, create an authentication token and set it in the context.
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Credentials are null for JWT-based auth
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // Update the SecurityContextHolder with the new authentication token.
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Set user ID as a request attribute if needed by controllers.
                    if (userDetails instanceof GLUserDetails) {
                        request.setAttribute("userId", ((GLUserDetails) userDetails).getId());
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("Authentication failed: The JWT token has expired.");
        } catch (SignatureException e) {
            log.warn("Authentication failed: JWT signature is invalid.");
        } catch (MalformedJwtException e) {
            log.warn("Authentication failed: JWT token is malformed.");
        } catch (UsernameNotFoundException e) {
            log.warn("Authentication failed: User in JWT token not found in the database.");
        } catch (Exception e) {
            // Catch any other unexpected errors during authentication.
            log.error("An unexpected error occurred during JWT authentication.", e);
        }

        // Continue the filter chain.
        filterChain.doFilter(request, response);
    }
}

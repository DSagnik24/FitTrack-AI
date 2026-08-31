package com.forgefit.forgeFit_Backend.security;

import com.forgefit.forgeFit_Backend.service.CustomUserDetailsService;
import com.forgefit.forgeFit_Backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        final String jwt;
        final String userEmail;

        // No Authorization header or incorrect format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT
        jwt = authHeader.substring(7);

        try {

            // Extract email from JWT
            userEmail = jwtService.extractUsername(jwt);
            System.out.println("JWT USER EMAIL: " + userEmail);

            // Authenticate only if user isn't already authenticated
            if (userEmail != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(userEmail);

                // Validate JWT
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("JWT IS VALID");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception exception) {
            System.out.println("JWT ERROR: " + exception.getMessage());

            // Invalid/expired JWT
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
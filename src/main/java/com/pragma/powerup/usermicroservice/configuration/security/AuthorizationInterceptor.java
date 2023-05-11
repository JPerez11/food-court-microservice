package com.pragma.powerup.usermicroservice.configuration.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements RequestInterceptor {

    private final HttpServletRequest httpServletRequest;

    @Override
    public void apply(RequestTemplate template) {
        // Get token from "Authorization" header
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // Check if header is present and in correct format (sample, "Bearer <token>")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " from the header to get just the token

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(null, token)
            );
            // Add the token to the outgoing request header
            template.header("Authorization", "Bearer " + token);
        }
    }
}

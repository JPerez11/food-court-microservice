package com.pragma.powerup.usermicroservice.configuration.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final List<String> excludedPrefixes = Arrays.asList("/auth/**", "/swagger-ui/**", "/actuator/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Send the HttpServletRequest to return the token without Bearer
        String token = getToken(request);

        if (token != null && JwtToken.validateToken(token)) {
            //Get authentication with the credentials in the token
            UsernamePasswordAuthenticationToken authenticationToken =
                    JwtToken.getAuthenticationToken(token);
            //Set authentication in Spring SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        //Get bearer token from Authorization header
        String bearerToken = request.getHeader("Authorization");
        //Validate that bearerToken is not null and starts with "Bearer"
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            //Remove the word "Bearer" from the bearerToken variable
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }
}

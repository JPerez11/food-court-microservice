package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtAuthorizationFilter.getToken;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ACCESS_TOKEN_SECRET;

public class ExtractAuthorization {

    private ExtractAuthorization() {}

    public static Long getAuthenticatedUserId() {
        // The ServletRequest is retrieved from the RequestContextHolder
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // The requestAttributes could throw a NullPointerException
        if (requestAttributes == null) {
            throw new NullPointerException();
        }
        // Get the HttpServletRequest from ServletRequestAttributes
        HttpServletRequest request = requestAttributes.getRequest();
        // Call the getToken() method from JwtAuthorizationFilter to get the token
        String token = getToken(request);
        // Decode token and get claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Get user id from claims
        return claims.get("id", Long.class);
    }

}

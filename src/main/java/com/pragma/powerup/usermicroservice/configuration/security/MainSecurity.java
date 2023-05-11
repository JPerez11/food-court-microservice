package com.pragma.powerup.usermicroservice.configuration.security;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtEntryPoint;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class MainSecurity {

    private final JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthorizationFilter jwtTokenFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/login", "/swagger-ui.html", "/swagger-ui/**",
                                        "/v3/api-docs/**", "/actuator/health")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin().disable()
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(jwtEntryPoint)
                )
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
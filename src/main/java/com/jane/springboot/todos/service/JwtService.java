package com.jane.springboot.todos.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateTokens(Map<String, Object> claims, UserDetails userDetails);
}

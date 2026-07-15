package com.example.hotelbooking.service;


import com.example.hotelbooking.model.enums.TokenType;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {
    String generateAccessToken(long userId,String userName, Collection<? extends GrantedAuthority> roles);

    String generateRefreshToken(long userId,String userName, Collection<? extends GrantedAuthority> roles);

    String extractUsername(String token, TokenType tokenType);

    long extractUserId(String token, TokenType tokenType);

    String extractTokenId(String token, TokenType tokenType);

    boolean isRefreshTokenActive(long userId, String tokenId);

    void revokeRefreshToken(long userId, String tokenId);

    void revokeAllRefreshTokens(long userId);
}

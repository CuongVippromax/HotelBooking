package com.example.hotelbooking.service;


import com.example.hotelbooking.model.enums.TokenType;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface JwtService {

    // Dữ liệu đọc từ 1 token sau khi verify. Parse 1 lần, dùng lại thay vì gọi nhiều hàm extract.
    record TokenPayload(long userId, String username, String tokenId, List<String> roles) {}

    String generateAccessToken(long userId,String userName, Collection<? extends GrantedAuthority> roles);

    String generateRefreshToken(long userId,String userName, Collection<? extends GrantedAuthority> roles);

    TokenPayload parse(String token, TokenType tokenType);

    boolean isRefreshTokenActive(long userId, String tokenId);

    void revokeRefreshToken(long userId, String tokenId);

    void revokeAllRefreshTokens(long userId);
}

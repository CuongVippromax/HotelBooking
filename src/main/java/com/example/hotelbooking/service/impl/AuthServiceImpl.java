package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.LoginRequest;
import com.example.hotelbooking.model.dto.response.TokenResponse;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.TokenType;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.AuthService;
import com.example.hotelbooking.service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "AUTH-SERVICE")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        // authenticate() sẽ tự load user qua UserDetailsService + so khớp password (BCrypt).
        // Sai username/password -> ném AuthenticationException, dừng luôn ở đây.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameorEmail(),
                        loginRequest.getPassword()));

        // Principal chính là entity User (đã implement UserDetails) -> lấy thẳng id/authorities, khỏi query lại DB.
        User user = (User) authentication.getPrincipal();
        return issueTokens(user);
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
        // 1. Parse + verify chữ ký, đồng thời ép đúng loại REFRESH_TOKEN.
        //    Token hết hạn/bị sửa/sai loại -> ném JwtException, dừng ở đây.
        JwtService.TokenPayload payload = jwtService.parse(refreshToken, TokenType.REFRESH_TOKEN);
        long userId = payload.userId();
        String tokenId = payload.tokenId();

        // 2. Reuse detection: refresh token hợp lệ về chữ ký nhưng jti KHÔNG còn trong Redis
        //    nghĩa là nó đã bị rotate (dùng rồi) hoặc bị revoke. Đây là dấu hiệu token bị đánh cắp
        //    -> thu hồi toàn bộ phiên của user, bắt đăng nhập lại.
        if (!jwtService.isRefreshTokenActive(userId, tokenId)) {
            log.warn("Refresh token reuse detected for userId={} tokenId={}. Revoking all sessions.", userId, tokenId);
            jwtService.revokeAllRefreshTokens(userId);
            throw new JwtException("Refresh token has been used or revoked");
        }

        // 3. Load user để lấy roles/authorities mới nhất (role có thể đã đổi từ lần login trước).
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 4. ROTATION: thu hồi refresh token vừa dùng, phát hành cặp token mới.
        //    Từ giờ token cũ sẽ fail ở bước 2 -> chống dùng lại.
        jwtService.revokeRefreshToken(userId, tokenId);
        return issueTokens(user);
    }

    @Override
    public void logout(String refreshToken) {
        try {
            // Parse để lấy userId + jti, rồi xóa jti khỏi Redis -> refresh token hết hiệu lực.
            JwtService.TokenPayload payload = jwtService.parse(refreshToken, TokenType.REFRESH_TOKEN);
            jwtService.revokeRefreshToken(payload.userId(), payload.tokenId());
            log.info("Logged out userId={} tokenId={}", payload.userId(), payload.tokenId());
        } catch (JwtException ex) {
            // Token đã hỏng/hết hạn/không hợp lệ -> coi như đã logout. Idempotent, không ném lỗi.
            log.warn("Logout with invalid refresh token: {}", ex.getMessage());
        }
    }

    // generateRefreshToken đã tự lưu jti vào Redis, không cần lưu thủ công ở đây.
    private TokenResponse issueTokens(User user) {
        return TokenResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities()))
                .refreshToken(jwtService.generateRefreshToken(user.getId(), user.getUsername(), user.getAuthorities()))
                .username(user.getUsername())
                .build();
    }
}


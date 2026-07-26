package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.model.enums.TokenType;
import com.example.hotelbooking.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Service
@Slf4j(topic = "JWT-SERVICE")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_ROLES = "roles";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private long refreshTokenExpiration;

    private final RedisTemplate<String, String> redisTemplate;

     @Override
    public String generateAccessToken(long userId, String userName, Collection<? extends GrantedAuthority> roles) {
        log.info("Generating access token for userId={} userName={}", userId, userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_TYPE, TokenType.ACCESS_TOKEN.name());
        claims.put(CLAIM_ROLES, roles.stream().map(GrantedAuthority::getAuthority).toList());
        return buildToken(claims, userId, userName, accessTokenExpiration, UUID.randomUUID().toString());
    }

    @Override
    public String generateRefreshToken(long userId, String userName, Collection<? extends GrantedAuthority> roles) {
        log.info("Generating refresh token for userId={} userName={}", userId, userName);
        // Mỗi refresh token có 1 định danh riêng (jti). Đây là chìa khóa của reuse detection:
        // ta lưu jti này vào Redis, refresh token chỉ hợp lệ khi jti của nó CÒN tồn tại trong Redis.
        String tokenId = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<>();
        // Đánh dấu loại token để sau này chặn việc dùng refresh token thay cho access token.
        claims.put(CLAIM_TYPE, TokenType.REFRESH_TOKEN.name());
        String token = buildToken(claims, userId, userName, refreshTokenExpiration, tokenId);
        // Lưu state vào Redis: key = {userId}:{jti}, TTL = hạn của refresh token.
        // Redis tự xóa key khi hết hạn -> không cần dọn rác thủ công.
        redisTemplate.opsForValue().set(
                refreshKey(userId, tokenId),
                userName,
                Duration.ofMillis(refreshTokenExpiration));
        return token;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TokenPayload parse(String token, TokenType tokenType) {
        // Parse + verify chữ ký 1 lần, đọc hết claim cần dùng. Tránh parse lại token nhiều lần.
        Claims claims = parseClaims(token);
        // Chặn dùng nhầm loại: ví dụ đưa refresh token vào endpoint cần access token.
        String actualType = claims.get(CLAIM_TYPE, String.class);
        if (!tokenType.name().equals(actualType)) {
            throw new JwtException(
                    "Token type mismatch: expected " + tokenType + " but got " + actualType);
        }
        // roles chỉ có ở access token; cast an toàn vì chính server ghi claim này ở generateAccessToken.
        Object rawRoles = claims.get(CLAIM_ROLES);
        List<String> roles = rawRoles == null ? List.of() : (List<String>) rawRoles;
        return new TokenPayload(
                Long.parseLong(claims.get("userId", String.class)),
                claims.getSubject(),
                claims.getId(),
                roles);
    }

    @Override
    public boolean isRefreshTokenActive(long userId, String tokenId) {
        // Refresh token chỉ "sống" khi jti của nó còn trong Redis.
        // Nếu đã bị rotate (xóa) hoặc bị revoke -> trả về false -> AuthService coi là dùng lại/không hợp lệ.
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshKey(userId, tokenId)));
    }

    @Override
    public void revokeRefreshToken(long userId, String tokenId) {
        log.info("Revoking refresh token userId={} tokenId={}", userId, tokenId);
        // Xóa 1 jti khỏi Redis. Dùng khi rotation (bỏ token cũ) hoặc logout 1 phiên.
        redisTemplate.delete(refreshKey(userId, tokenId));
    }

    @Override
    public void revokeAllRefreshTokens(long userId) {
        log.warn("Revoking ALL refresh tokens for userId={}", userId);
        // Xóa toàn bộ refresh token của user. Gọi khi phát hiện reuse (token bị đánh cắp)
        // để vô hiệu hóa mọi phiên -> bắt user đăng nhập lại.
        // Lưu ý: keys(pattern) quét toàn bộ Redis (O(N), blocking). Chấp nhận được ở quy mô nhỏ,
        // production nên dùng familyId + Redis SET để revoke theo nhóm thay vì scan pattern.
        Set<String> keys = redisTemplate.keys(refreshKey(userId, "*"));
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String buildToken(Map<String, Object> claims, long userId, String userName, long expirationMillis, String tokenId) {
        long now = System.currentTimeMillis();
        claims.put("userId", String.valueOf(userId));
        return Jwts.builder()
                .claims(claims)
                .id(tokenId)
                .subject(userName)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMillis))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Claims parseClaims(String token) {
        // verifyWith kiểm tra chữ ký HMAC + tự động ném exception nếu token hết hạn/bị sửa.
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        // Cùng 1 secret cho cả access & refresh. Việc phân biệt 2 token dựa vào claim "type"
        // và trạng thái trong Redis, không dựa vào key khác nhau.
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private String refreshKey(long userId, String tokenId) {
        return userId + ":" + tokenId;
    }
}

package com.example.hotelbooking.config.filter;

import com.example.hotelbooking.model.enums.TokenType;
import com.example.hotelbooking.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j(topic = "JWT-AUTHENTICATION-FILTER")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        try {
            // Ép đúng loại ACCESS_TOKEN: đưa refresh token vào đây sẽ ném JwtException.
            // parse() verify chữ ký + hạn 1 lần rồi đọc hết claim, tới đây token chắc chắn hợp lệ.
            JwtService.TokenPayload payload = jwtService.parse(token, TokenType.ACCESS_TOKEN);

            // Chỉ set context nếu chưa có (tránh ghi đè khi request đã được xác thực trước đó).
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                List<SimpleGrantedAuthority> authorities = payload.roles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // principal = username. Không cần credentials (đã xác thực qua token) -> để null.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(payload.username(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Khai báo với Spring Security: request này đã xác thực, có các quyền trên.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authenticated userId={} username={} roles={}", payload.userId(), payload.username(), payload.roles());
            }
        } catch (JwtException ex) {
            // Token hỏng/hết hạn/sai loại -> KHÔNG set context, cũng không chặn ở đây.
            // Cứ để SecurityFilterChain quyết định: endpoint public vẫn qua, endpoint cần auth -> 401.
            log.warn("Invalid JWT: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}

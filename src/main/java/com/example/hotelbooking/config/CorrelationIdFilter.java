package com.example.hotelbooking.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Gắn một correlation ID cho mỗi request để trace log xuyên suốt.
 * Chạy SỚM NHẤT trong chain (trước cả JwtAuthenticationFilter) để mọi log sau đó đều có ID.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j(topic = "CORRELATION-ID-FILTER")
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Ưu tiên ID client gửi lên (để nối trace qua nhiều service); không có thì tự sinh.
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Đưa vào MDC -> mọi dòng log trong request này tự động kèm correlationId (nếu pattern log có %X{correlationId}).
        MDC.put(MDC_KEY, correlationId);
        // Trả lại cho client để họ đối chiếu khi báo lỗi.
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("{} {} -> {} ({}ms)",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
            // Bắt buộc clear MDC: thread được tái sử dụng từ pool, không dọn sẽ rò ID sang request khác.
            MDC.remove(MDC_KEY);
        }
    }
}

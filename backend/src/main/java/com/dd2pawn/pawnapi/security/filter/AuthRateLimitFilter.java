package com.dd2pawn.pawnapi.security.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dd2pawn.pawnapi.exception.TooManyLoginAttemptsException;

import jakarta.servlet.FilterChain;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> registerCache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(request.getMethod()) && "/api/auth/register".equals(request.getRequestURI())) {
            if (!handleRegisterAttempt(request, response)) {
                return;
            }
        }
            filterChain.doFilter(request, response);
    }

    private boolean handleRegisterAttempt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ip = getClientIp(request);
        Bucket bucket = registerCache.computeIfAbsent(ip, this::newRegisterBucket);

        if (!bucket.tryConsume(1)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many registration attempts. Please try again later.");
            return false;
        }
        return true;
    }

    private Bucket newRegisterBucket(String key) {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(5)
                                .refillIntervally(5, Duration.ofMinutes(5))
                                .build())
                .build();
    }

    public String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}

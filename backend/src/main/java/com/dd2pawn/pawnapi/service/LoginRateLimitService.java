package com.dd2pawn.pawnapi.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.dd2pawn.pawnapi.exception.TooManyLoginAttemptsException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Service
public class LoginRateLimitService {
    private final Map<String, Bucket> loginCache = new ConcurrentHashMap<>();

    public void consumeFailedAttempt(String ip) {
        Bucket bucket = loginCache.computeIfAbsent(ip, this::newLoginBucket);

        if (!bucket.tryConsume(1)) {
            throw new TooManyLoginAttemptsException();
        }
    }

    private Bucket newLoginBucket(String key) {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(5)
                                .refillIntervally(5, Duration.ofMinutes(1))
                                .build())
                .build();
    }

    public void checkBlockedIp(String ip) {
        Bucket bucket = loginCache.computeIfAbsent(ip, this::newLoginBucket);

        if (bucket.getAvailableTokens() == 0) {
            throw new TooManyLoginAttemptsException();
        }
    }
}

package com.ragchat.storage.service;

import com.ragchat.storage.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Service responsible for rate limiting logic using a Sliding Window algorithm
 * backed by Redis.
 * <p>
 * This service ensures that a specific key (e.g., API Key) does not exceed a
 * defined number of requests
 * within a sliding time window.
 */
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    @Value("${app.rate-limit.requests:10}")
    private int maxRequests;

    @Value("${app.rate-limit.window-seconds:60}")
    private int windowSeconds;

    /**
     * Checks if a request is allowed for the given key based on the sliding window
     * algorithm.
     *
     * @param key The unique identifier for the requester (e.g., API Key).
     * @return true if the request is allowed, false otherwise.
     */
    public boolean isAllowed(String key) {
        String redisKey = AppConstants.REDIS_RATE_LIMIT_PREFIX + key;
        long currentTime = Instant.now().toEpochMilli();
        long windowStart = currentTime - (windowSeconds * 1000L);

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.removeRangeByScore(redisKey, 0, windowStart);

        Long count = zSetOperations.zCard(redisKey);

        if (count != null && count < maxRequests) {
            zSetOperations.add(redisKey, String.valueOf(currentTime), currentTime);
            redisTemplate.expire(redisKey, windowSeconds + 10L, TimeUnit.SECONDS);
            return true;
        }

        return false;
    }
}

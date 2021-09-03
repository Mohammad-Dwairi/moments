package com.mdwairy.momentsapi.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JWTRepository {

    public static final String SET_NAME = "jwt";
    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String token) {
        redisTemplate.opsForSet().add(SET_NAME, token);
    }

    public boolean isExists(String token) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(SET_NAME, token));
    }
}

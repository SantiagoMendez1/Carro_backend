package com.ingesoft.carro.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;

    public <V> void set(String cacheName, String key, V value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(cacheName+key, value);
        redisTemplate.expire(cacheName+key, timeout, timeUnit);
    }

    public Boolean hasKey(String cacheName, String key) {
        return redisTemplate.hasKey(cacheName+key);
    }

    public <V> V get(String cacheName, String key) {
        return (V) redisTemplate.opsForValue().get(cacheName+key);
    }
}
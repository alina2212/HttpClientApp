package com.example.httpclient.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class CacheService {
    private static final Logger logger = LogManager.getLogger(CacheService.class);

    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public void put(String key, String value) {
        cache.put(key, value);
        logger.info("Кэш обновлён: {}", key);
    }

    public String get(String key) {
        String value = cache.getIfPresent(key);
        if (value != null) {
            logger.info("Данные взяты из кэша: {}", key);
        }
        return value;
    }

    public boolean contains(String key) {
        return cache.getIfPresent(key) != null;
    }

    public void remove(String key) {
        cache.invalidate(key);
        logger.info("Кэш очищен: {}", key);
    }
}
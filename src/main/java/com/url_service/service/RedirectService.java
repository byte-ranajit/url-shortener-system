package com.url_service.service;

import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedirectService {
    private RedisTemplate<String, String> redisTemplate;
    private UrlRepository urlRepository;

    @Autowired
    public RedirectService(RedisTemplate<String, String> redisTemplate, UrlRepository urlRepository) {
        this.redisTemplate = redisTemplate;
        this.urlRepository = urlRepository;
    }


    public String getLongUrl(String shortCode) {
       String cached = redisTemplate.opsForValue().get(shortCode);
       if (cached != null)
            return cached;

       Url url = urlRepository.findByShortCode(shortCode).orElseThrow();
       redisTemplate.opsForValue().set(shortCode, url.getLongUrl());
       return url.getLongUrl();
    }

}

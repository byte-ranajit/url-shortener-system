package com.url_service.service;

import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class RedirectService {

    private RedisTemplate<String, String> redisTemplate;
    private UrlRepository urlRepository;

    public String getLongUrl(String shortCode) {
       String cached = redisTemplate.opsForValue().get(shortCode);
       if (cached != null)
            return cached;

       Url url = urlRepository.findByShortCode(shortCode)
               .orElseThrow(() -> new RuntimeException("Short URL not found"));
        if (url.getExpirationTime() != null &&
                url.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Link expired");
        }
       redisTemplate.opsForValue().set(shortCode, url.getLongUrl());
       return url.getLongUrl();
    }

}

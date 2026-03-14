package com.url_service.service;

import com.url_service.dto.UrlRequest;
import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import com.url_service.util.Base62Encoder;
import com.url_service.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UrlService {

    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final UrlRepository urlRepository;
    private final Base62Encoder encoder;

    @Autowired
    public UrlService(UrlRepository urlRepository, Base62Encoder encoder, SnowflakeIdGenerator snowflakeIdGenerator) {
        this.urlRepository = urlRepository;
        this.encoder = encoder;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    public String shortenUrl(UrlRequest request) {

        long uniqeId = snowflakeIdGenerator.nextId();
        String shortCode = encoder.encode(uniqeId);
        Url url = new Url();

        url.setLongUrl(request.getLongUrl());
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);

        if (request.getExpirationTime() != null) {
            url.setExpirationTime(LocalDateTime.parse(request.getExpirationTime(), DateTimeFormatter.ISO_DATE_TIME));
        }

        url = urlRepository.save(url);

        url.setShortCode(shortCode);

        urlRepository.save(url);

        return shortCode;
    }
}

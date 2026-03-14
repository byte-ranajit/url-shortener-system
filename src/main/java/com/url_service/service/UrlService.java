package com.url_service.service;

import com.url_service.dto.UrlRequest;
import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import com.url_service.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UrlService {

    private UrlRepository urlRepository;
    private Base62Encoder encoder;

    @Autowired
    public UrlService(UrlRepository urlRepository, Base62Encoder encoder) {
        this.urlRepository = urlRepository;
        this.encoder = encoder;
    }

    public String shortenUrl(UrlRequest request) {

        Url url = new Url();

        url.setLongUrl(request.getLongUrl());
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);

        if (request.getExpirationTime() != null) {
            url.setExpirationTime(LocalDateTime.parse(request.getExpirationTime(), DateTimeFormatter.ISO_DATE_TIME));
        }

        url = urlRepository.save(url);

        String shortCode = encoder.encode(url.getId());

        url.setShortCode(shortCode);

        urlRepository.save(url);

        return shortCode;
    }
}

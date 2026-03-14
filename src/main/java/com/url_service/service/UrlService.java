package com.url_service.service;

import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import com.url_service.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UrlService {
    private UrlRepository urlRepository;
    private Base62Encoder encoder;
    @Autowired
    public UrlService(UrlRepository urlRepository, Base62Encoder encoder) {
        this.urlRepository = urlRepository;
        this.encoder = encoder;
    }
    public String shortenUrl(String longUrl) {
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setCreatedAt(LocalDateTime.now());

        url = urlRepository.save(url);
        String shortCode = encoder.encode(url.getId());
        url.setShortCode(shortCode);
        urlRepository.save(url);
        return shortCode;
    }
}

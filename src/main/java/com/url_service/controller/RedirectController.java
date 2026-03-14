package com.url_service.controller;

import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class RedirectController {

    private UrlRepository urlRepository;

    @Autowired
    public RedirectController(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect (@PathVariable String code) {
        Url url = urlRepository.findByShortCode(code)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        if (url.getExpirationTime() != null && url.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Link expired");
        }
        if (url.getClickCount() == null){
            url.setClickCount(0L);
        }
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                URI.create(url.getLongUrl())
        );
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}

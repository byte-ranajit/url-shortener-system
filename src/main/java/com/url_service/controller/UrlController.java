package com.url_service.controller;

import com.url_service.dto.UrlRequest;
import com.url_service.dto.UrlResponse;
import com.url_service.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    private UrlService urlService;
    @Autowired
    public void setUrlService(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("shorten")
    public UrlResponse shorten(@RequestBody UrlRequest request){
        String shortCode = urlService.shortenUrl((request.getLongUtl()));
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new UrlResponse(shortUrl, request.getLongUtl(), shortCode);
    }
}

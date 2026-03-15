package com.url_service.controller;

import com.url_service.dto.UrlRequest;
import com.url_service.dto.UrlResponse;
import com.url_service.model.Url;
import com.url_service.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
@AllArgsConstructor
public class UrlController {

    private UrlService urlService;

    @PostMapping("/shorten")
    public UrlResponse shorten(@RequestBody UrlRequest request){
        String shortCode = urlService.shortenUrl((request));
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new UrlResponse(shortUrl, request.getLongUrl(), shortCode);
    }

}

package com.url_service.controller;

import com.url_service.controllerhelper.RedirectControllerHelper;
import com.url_service.model.Url;
import com.url_service.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    private RedirectControllerHelper redirectControllerHelper ;

    @Autowired
    public RedirectController(UrlRepository urlRepository, RedirectControllerHelper redirectControllerHelper){
        this.urlRepository = urlRepository;
        this.redirectControllerHelper = redirectControllerHelper;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect (@PathVariable String code, HttpServletRequest request) {
        HttpHeaders headers = redirectControllerHelper.redirectUrl(code);
        redirectControllerHelper.getUrlClicks(code, request);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}

package com.url_service.controllerhelper;

import com.url_service.model.Url;
import com.url_service.model.UrlClick;
import com.url_service.repository.UrlClickRepository;
import com.url_service.repository.UrlRepository;
import com.url_service.service.RedirectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;

@Component
public class RedirectControllerHelper {

    UrlRepository urlRepository;
    UrlClickRepository urlClickRepository;
    RedirectService redirectService;

    @Autowired
    RedirectControllerHelper(UrlRepository urlRepository, UrlClickRepository urlClickRepository, RedirectService redirectService){
        this.urlClickRepository = urlClickRepository;
        this.urlRepository = urlRepository;
        this.redirectService = redirectService;
    }

    public HttpHeaders redirectUrl(String code){
        String longUrl = redirectService.getLongUrl(code);
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
                URI.create(longUrl)
        );

        return headers;
    }

    public void getUrlClicks(String code, HttpServletRequest request){
        UrlClick click = new UrlClick();
        click.setShortCode(code);
        click.setClickedAt(LocalDateTime.now());
        click.setIpAddress(request.getRemoteAddr());
        click.setUserAgent(request.getHeader("User-Agent"));
        urlClickRepository.save(click);
    }
}

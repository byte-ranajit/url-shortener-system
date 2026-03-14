package com.url_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlResponse {
    private String shortUrl;
    private String longUrl;
    private String shortCode;
}

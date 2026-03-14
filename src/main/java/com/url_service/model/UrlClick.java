package com.url_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "url_clicks")
public class UrlClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortCode;
    private LocalDateTime clickedAt;
    private String ipAddress;
    private String userAgent;

}

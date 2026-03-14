package com.url_service.repository;

import com.url_service.model.Url;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends BaseJpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}

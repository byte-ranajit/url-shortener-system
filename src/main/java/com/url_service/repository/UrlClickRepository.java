package com.url_service.repository;

import com.url_service.model.UrlClick;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlClickRepository extends BaseJpaRepository<UrlClick, Long> {
}

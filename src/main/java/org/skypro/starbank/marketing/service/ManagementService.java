package org.skypro.starbank.marketing.service;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ManagementService {
    private final CacheManager cacheManager;

    public ManagementService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Operation(summary = "Очистка всех кэшей",
            description = "Полностью очищает все закэшированные результаты рекомендаций")
    public void clearAllCaches() {
        cacheManager
                .getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}

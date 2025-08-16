package org.skypro.starbank.marketing.service.rule;

import org.skypro.starbank.marketing.dto.RecommendationProduct;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional<RecommendationProduct> recommendation(UUID userId);
}
package org.skypro.starbank.marketing.recommendation.component;

import org.skypro.starbank.marketing.recommendation.dto.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional<Recommendation> getRecommendation(UUID userId);
}

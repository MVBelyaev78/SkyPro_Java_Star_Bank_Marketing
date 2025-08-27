package org.skypro.starbank.marketing.component.recommendation.rule;

import org.skypro.starbank.marketing.dto.recommendation.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional<Recommendation> getRecommendation(UUID userId);
}

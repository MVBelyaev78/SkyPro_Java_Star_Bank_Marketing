package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional<Recommendation> getRecommendation(UUID userId);
}

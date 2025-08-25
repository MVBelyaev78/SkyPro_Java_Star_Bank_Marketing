package org.skypro.starbank.marketing.component.recommendation;

import org.skypro.starbank.marketing.dto.recommendation.Recommendation;

import java.util.Collection;
import java.util.UUID;

public interface RecommendationCollect {
    Collection<Recommendation> getRecommendations(UUID userId);
}

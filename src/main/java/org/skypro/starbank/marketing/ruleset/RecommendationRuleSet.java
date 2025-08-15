package org.skypro.starbank.marketing.ruleset;

import org.skypro.starbank.marketing.model.recommendation.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(UUID userID);
}

package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

public abstract class SuperclassRecommendationRule implements RecommendationRule {
    private final RecommendationsRepository recommendationsRepository;

    public SuperclassRecommendationRule(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (recommendationsRepository
                .getSearchResult(userId.toString())
                .getResult()) {
            recommendation = Optional.of(getNewRecommendation());
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }

    public abstract Recommendation getNewRecommendation();
}

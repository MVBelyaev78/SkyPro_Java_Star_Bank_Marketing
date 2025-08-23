package org.skypro.starbank.marketing.recommendation.component;

import org.skypro.starbank.marketing.recommendation.dto.Recommendation;
import org.skypro.starbank.marketing.recommendation.repository.FixedRulesRepository;
import org.skypro.starbank.marketing.recommendation.dto.SearchResult;

import java.util.Optional;
import java.util.UUID;

public abstract class RecommendationRuleImpl implements RecommendationRule {
    private final FixedRulesRepository recommendationsRepository;

    public RecommendationRuleImpl(FixedRulesRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public FixedRulesRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (getSearchResult(userId.toString()).getResult()) {
            recommendation = Optional.of(getNewRecommendation());
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }

    protected abstract SearchResult getSearchResult(String userId);

    protected abstract Recommendation getNewRecommendation();
}

package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.result.SearchResult;

import java.util.Optional;
import java.util.UUID;

public abstract class RecommendationRuleImpl implements RecommendationRule {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (getSearchResult(userId.toString()).result()) {
            recommendation = Optional.of(getNewRecommendation());
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }

    protected abstract SearchResult getSearchResult(String userId);

    protected abstract Recommendation getNewRecommendation();
}

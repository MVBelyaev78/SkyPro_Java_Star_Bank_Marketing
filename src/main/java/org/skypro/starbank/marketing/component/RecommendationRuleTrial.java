package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.result.SearchResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleTrial implements RecommendationRule {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleTrial(RecommendationsRepository recommendationsRepository) {
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
            recommendation = Optional.of(new Recommendation(
                    "Trial",
                    "7bcad462-870d-4b70-8ea3-e7f5bf5a23e5",
                    "Пробный вариант рекомендации, делайте по образу и подобию"));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}

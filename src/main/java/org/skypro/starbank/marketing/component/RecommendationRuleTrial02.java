package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.result.SearchResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleTrial02 implements RecommendationRule {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleTrial02(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    public SearchResult getSearchResult(UUID userId) {
        return recommendationsRepository.searchResultStringParam(userId.toString(),
                "SELECT NOT EXISTS (SELECT NULL " +
                        "FROM public.transactions t JOIN public.products p ON p.id = t.product_id " +
                        "WHERE p.\"TYPE\" = 'INVEST' AND t.user_id = ?) AS \"result\"");
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (getSearchResult(userId).getResult()) {
            recommendation = Optional.of(new Recommendation(
                    "Trial 2",
                    "7bcad462-870d-4b70-8ea3-e7f5bf5a23e5",
                    "Пробный вариант рекомендации, вторая версия"));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}

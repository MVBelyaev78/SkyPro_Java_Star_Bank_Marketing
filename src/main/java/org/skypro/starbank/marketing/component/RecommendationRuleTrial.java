package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.result.SearchResult;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleTrial implements RecommendationRule {
   private final  RecommendationsRepository recommendationsRepository;

    public RecommendationRuleTrial(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public RecommendationsRepository getRecommendationsRepository() {
        return recommendationsRepository;
    }

    public SearchResult getSearchResult(UUID userId) {
        return recommendationsRepository.searchResultStringParam(userId.toString(),
                "SELECT EXISTS (SELECT NULL " +
                        "FROM public.transactions t JOIN public.products p ON p.id = t.product_id " +
                        "WHERE p.\"TYPE\" = 'DEBIT' AND t.user_id = ?) AS \"result\"");
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        Optional<Recommendation> recommendation;
        if (getSearchResult(userId).getResult()) {
            recommendation = Optional.of(new Recommendation(
                    "Trial",
                    "d108934b-b6e5-4aa6-a0b5-6a5d33fbf162",
                    "Пробный вариант рекомендации"));
        } else {
            recommendation = Optional.empty();
        }
        return recommendation;
    }
}

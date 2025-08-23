package org.skypro.starbank.marketing.component.recommendation;

import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.stereotype.Component;

@Component
public class RecRuleTopSaving extends RecommendationRuleImpl {
    public RecRuleTopSaving(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected SearchResult getSearchResult(String userId) {
        return getRecommendationsRepository().getSearchResultTopSaving(userId);
    }

    @Override
    protected Recommendation getNewRecommendation() {
        return new Recommendation(
                "Top Saving",
                "59efc529-2fff-41af-baff-90ccd7402925",
                "Откройте свою собственную «Копилку» с нашим банком!");
    }
}

package org.skypro.starbank.marketing.component;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.result.SearchResult;
import org.springframework.stereotype.Component;

@Component
public class RecRuleSimpleLoan extends RecommendationRuleImpl {
    public RecRuleSimpleLoan(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    public SearchResult getSearchResult(String userId) {
        return getRecommendationsRepository().getSearchResultSimpleLoan(userId);
    }

    @Override
    public Recommendation getNewRecommendation() {
        return new Recommendation(
                "Простой кредит",
                "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                "Откройте мир выгодных кредитов с нами!");
    }
}

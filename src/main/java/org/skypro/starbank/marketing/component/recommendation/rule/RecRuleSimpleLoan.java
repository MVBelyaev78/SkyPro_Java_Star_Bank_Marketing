package org.skypro.starbank.marketing.component.recommendation.rule;

import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.repository.FixedRulesRepository;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.stereotype.Component;

@Component
public class RecRuleSimpleLoan extends RecommendationRuleImpl {
    public RecRuleSimpleLoan(FixedRulesRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected SearchResult getSearchResult(String userId) {
        return getRecommendationsRepository().getSearchResultSimpleLoan(userId);
    }

    @Override
    protected Recommendation getNewRecommendation() {
        return new Recommendation(
                "Простой кредит",
                "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                "Откройте мир выгодных кредитов с нами!");
    }
}

package org.skypro.starbank.marketing.recommendation.component;

import org.skypro.starbank.marketing.recommendation.dto.SearchResult;
import org.skypro.starbank.marketing.recommendation.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynRecRuleUserOf extends DynamicRecommendationRules {
    public DynRecRuleUserOf(DynamicRulesRepository dynamicRulesRepository) {
        super(dynamicRulesRepository);
    }

    @Override
    protected SearchResult getComponentMethod(String query, String userId, List<String> arguments, Boolean negate) {
        SearchResult searchResult = new SearchResult(true);
        if (query.equals("USER_OF")) {
            searchResult = getDynamicRulesRepository().getUserOfQuery(userId, arguments, negate);
        }
        return searchResult;
    }
}

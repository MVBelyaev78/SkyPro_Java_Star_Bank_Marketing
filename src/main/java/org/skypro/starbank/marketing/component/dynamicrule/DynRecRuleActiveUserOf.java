package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.SearchParameters;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

@Component
public class DynRecRuleActiveUserOf implements DynRecRuleSearchMethod {
    private final DynamicRulesRepository dynamicRulesRepository;

    public DynRecRuleActiveUserOf(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    @Override
    public String getQuery() {
        return "ACTIVE_USER_OF";
    }

    @Override
    public SearchResult getSearchMethod(SearchParameters searchParameters) {
        return dynamicRulesRepository.getActiveUserOfQuery(searchParameters);
    }
}

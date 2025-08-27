package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;

import java.util.List;

public class DynamicRecRulTransactionSumCompare implements DynRecRuleSearchMethod {
    private final DynamicRulesRepository dynamicRulesRepository;

    public DynamicRecRulTransactionSumCompare(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    @Override
    public String getQuery() {
        return "TRANSACTION_SUM_COMPARE";
    }

    @Override
    public SearchResult getSearchMethod(String userId, List<String> arguments, Boolean negate) {
        return null;
    }

    @Override
    public SearchResult getSearchMethod(String userId, List<String> arguments, Boolean negate) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (arguments == null || arguments.isEmpty()) {
            throw new IllegalArgumentException("Arguments cannot be null or empty");
        }

        return dynamicRulesRepository.getActiveUserOfQuery(userId, arguments, negate);
    }
}

package org.skypro.starbank.marketing.component.dynamicrule.dynrule;

import org.skypro.starbank.marketing.dto.dynamicrule.SearchParameters;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

@Component
public class DynRecRuleTransactionSumCompareDepositWithdraw implements DynRecRule {
    private final DynamicRulesRepository dynamicRulesRepository;

    public DynRecRuleTransactionSumCompareDepositWithdraw(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    @Override
    public String getQuery() {
        return "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    }

    @Override
    public SearchResult getSearchMethod(SearchParameters searchParameters) {
        return dynamicRulesRepository.getTransactionSumCompareDepositWithdraw(searchParameters);
    }
}

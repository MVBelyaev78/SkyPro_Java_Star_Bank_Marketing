package org.skypro.starbank.marketing.component.dynamicrule;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.repository.DynamicRulesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynRecRuleTransactionSumCompareDepositWithdraw implements DynRecRuleSearchMethod {
    private final DynamicRulesRepository dynamicRulesRepository;

    public DynRecRuleTransactionSumCompareDepositWithdraw(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    @Override
    public String getQuery() {
        return "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    }

    @Override
    public SearchResult getSearchMethod(String userId, List<String> arguments, Boolean negate) {
        return dynamicRulesRepository.getTransactionSumCompareDepositWithdraw(userId, arguments, negate);
    }
}

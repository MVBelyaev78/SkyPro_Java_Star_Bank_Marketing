package org.skypro.starbank.marketing.configuration.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DynamicRulesDatabaseEmulator implements DynamicRulesDatabase {
    Collection<DynamicRule> rules;

    public DynamicRulesDatabaseEmulator() {
        Collection<QueryType> queryTypes = new ArrayList<>();
        queryTypes.add(new QueryType("USER_OF", Collections.singletonList("DEBIT"), false));
        queryTypes.add(new QueryType("ACTIVE_USER_OF", Collections.singletonList("CREDIT"), true));
        rules = new ArrayList<DynamicRule>(Collections.singleton(new DynamicRule(
                UUID.fromString("7bf8faa7-fdcc-4bbb-b222-1b25fe436e61"),
                "Пробная динамическая рекомендация",
                UUID.fromString("5f3062c1-9f37-4e98-8912-d97dcafba800"),
                "текст рекомендации",
                queryTypes)));
    }

    @Override
    public DynamicRule addRule(DynamicRule ruleSource) {
        DynamicRule result = new DynamicRule(
                UUID.randomUUID(),
                ruleSource.getName(),
                ruleSource.getRecommendationUuid(),
                ruleSource.getText(),
                ruleSource.getRule());
        rules.add(result);
        return result;
    }

    @Override
    public Collection<DynamicRule> getRules() {
        return rules;
    }

    @Override
    public void deleteRule(UUID recommendationUuid) {
        rules.removeIf(rule -> rule.getRecommendationUuid().equals(recommendationUuid));
    }
}

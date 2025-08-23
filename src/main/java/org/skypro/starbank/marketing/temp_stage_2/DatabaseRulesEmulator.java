package org.skypro.starbank.marketing.temp_stage_2;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Configuration
public class DatabaseRulesEmulator implements DatabaseRules {
    Collection<DynamicRule> rules = new ArrayList<>();

    @Override
    public DynamicRule addRules(DynamicRule ruleSource) {
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
    public ListingRules getListingRules() {
        return new ListingRules(rules);
    }

    @Override
    public void deleteRule(UUID recommendationUuid) {
        rules.removeIf(rule -> rule.getRecommendationUuid() == recommendationUuid);
    }
}

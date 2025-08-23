package org.skypro.starbank.marketing.temp_stage_2;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DynamicRuleService {
    private final DatabaseRules databaseRules;

    public DynamicRuleService(DatabaseRules databaseRules) {
        this.databaseRules = databaseRules;
    }

    public DynamicRule addRules(DynamicRule rule) {
        return databaseRules.addRules(rule);
    }

    public ListingRules getListingRules() {
        return databaseRules.getListingRules();
    }

    public void deleteRule(UUID recommendationUuid) {
        databaseRules.deleteRule(recommendationUuid);
    }
}

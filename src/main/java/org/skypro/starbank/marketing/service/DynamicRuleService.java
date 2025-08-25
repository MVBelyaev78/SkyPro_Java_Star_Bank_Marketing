package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.configuration.dynamicrule.DynamicRulesDatabase;
import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.ListingRules;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DynamicRuleService {
    private final DynamicRulesDatabase databaseRules;

    public DynamicRuleService(DynamicRulesDatabase databaseRules) {
        this.databaseRules = databaseRules;
    }

    public DynamicRule addRules(DynamicRule rule) {
        return databaseRules.addRule(rule);
    }

    public ListingRules getListingRules() {
        return new ListingRules(databaseRules.getRules());
    }

    public void deleteRule(UUID recommendationUuid) {
        databaseRules.deleteRule(recommendationUuid);
    }
}

package org.skypro.starbank.marketing.dynamicrule.service;

import org.skypro.starbank.marketing.dynamicrule.configuration.DynamicRulesDatabase;
import org.skypro.starbank.marketing.dynamicrule.dto.DynamicRule;
import org.skypro.starbank.marketing.dynamicrule.dto.ListingRules;
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

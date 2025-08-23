package org.skypro.starbank.marketing.configuration.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.ListingRules;
import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;

import java.util.UUID;

public interface DynamicRulesDatabase {
    DynamicRule addRules(DynamicRule rule);
    ListingRules getListingRules();
    void deleteRule(UUID recommendationUuid);
}

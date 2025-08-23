package org.skypro.starbank.marketing.dynamicrule.configuration;

import org.skypro.starbank.marketing.dynamicrule.dto.ListingRules;
import org.skypro.starbank.marketing.dynamicrule.dto.DynamicRule;

import java.util.UUID;

public interface DynamicRulesDatabase {
    DynamicRule addRules(DynamicRule rule);
    ListingRules getListingRules();
    void deleteRule(UUID recommendationUuid);
}

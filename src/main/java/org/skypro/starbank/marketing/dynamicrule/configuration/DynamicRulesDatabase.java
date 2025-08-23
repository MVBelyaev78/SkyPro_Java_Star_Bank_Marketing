package org.skypro.starbank.marketing.dynamicrule.configuration;

import org.skypro.starbank.marketing.dynamicrule.dto.DynamicRule;

import java.util.Collection;
import java.util.UUID;

public interface DynamicRulesDatabase {
    DynamicRule addRules(DynamicRule rule);
    Collection<DynamicRule> getRules();
    void deleteRule(UUID recommendationUuid);
}

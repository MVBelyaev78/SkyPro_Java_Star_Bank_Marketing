package org.skypro.starbank.marketing.configuration.dynamicrule;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;

import java.util.Collection;
import java.util.UUID;

public interface DynamicRulesDatabase {
    DynamicRule addRule(DynamicRule rule);

    Collection<DynamicRule> getRules();

    void deleteRule(UUID recommendationUuid);
}
